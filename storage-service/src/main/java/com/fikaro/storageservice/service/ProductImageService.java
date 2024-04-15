package com.fikaro.storageservice.service;

import com.fikaro.storageservice.dto.ProductImagesDto;
import com.fikaro.storageservice.dto.SwiperImagesDto;
import com.fikaro.storageservice.entity.ProductEtt;
import com.fikaro.storageservice.entity.ProductImageEtt;
import com.fikaro.storageservice.entity.SwiperImagesEtt;
import com.fikaro.storageservice.repository.ProductImageRepository;
import com.fikaro.storageservice.repository.ProductRepository;
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
public class ProductImageService {

    @Autowired
    private ProductImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;

    public Long uploadImageWithProductId(Long productId, MultipartFile file) throws IOException {

        Optional<ProductEtt> productEtt = productRepository.findById(productId);

        if (productEtt.isEmpty()) {
            return null;
        }
        ProductImageEtt savedImage = imageRepository.save(ProductImageEtt.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).product(productEtt.get()).build());
        return savedImage.getId();
    }

    public byte[] getFirstImage(Long id){
        Optional<ProductImageEtt> dbImage = imageRepository.findByProduct_Id(id).stream().findFirst();
        return ImageUtils.decompressImage(dbImage.get().getImageData());
    }

    public byte[] getImageById(Long id){
        Optional<ProductImageEtt> dbImage = imageRepository.findById(id);
        return ImageUtils.decompressImage(dbImage.get().getImageData());
    }

    public List<ProductImagesDto> getALlImages(){
        List<ProductImageEtt> productImageEttList = imageRepository.findAll();
        return productImageEttList.stream().map(this::mapModelToResponse).toList();
    }

    public List<Long> getImagesIdByProductId(Long id){
        return imageRepository.findImageIds(id);
    }

    public List<ProductImagesDto> getALlImagesById(Long productId){
        List<ProductImageEtt> productImageEttList = imageRepository.findByProduct_Id(productId);
        return productImageEttList.stream().map(this::mapModelToResponse).toList();
    }

    public boolean deleteImageByProductId(Long id){

        try {
            imageRepository.deleteByProduct_Id(id);
            return true;
        } catch (Exception e) {

            log.error("Image with id {} not found", id);
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
