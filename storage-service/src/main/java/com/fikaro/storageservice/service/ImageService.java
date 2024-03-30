package com.fikaro.storageservice.service;

import com.fikaro.storageservice.dto.ProductImagesDto;
import com.fikaro.storageservice.entity.ProductImageEtt;
import com.fikaro.storageservice.repository.ImageRepository;
import com.fikaro.storageservice.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public String uploadImage(MultipartFile file) throws IOException {

        ProductImageEtt imageData = imageRepository.save(ProductImageEtt.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ProductImageEtt> dbImage = imageRepository.findByName(fileName);
        return ImageUtils.decompressImage(dbImage.get().getImageData());
    }

    public List<ProductImagesDto> getALlImages(){
        List<ProductImageEtt> imageDataList = imageRepository.findAll();
        return imageDataList.stream().map(this::mapModelToResponse).toList();
    }

    public boolean deleteImageById(Long id){
        Optional<ProductImageEtt> optionalImageData = imageRepository.findById(id);

        if (optionalImageData.isPresent()) {
            // Delete from the database
            imageRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private ProductImagesDto mapModelToResponse(ProductImageEtt imageData){
        return ProductImagesDto.builder()
                .id(imageData.getId())
                .name(imageData.getName())
                .type(imageData.getType())
                .imageData(imageData.getImageData())
                .build();
    }

}
