package com.fikaro.storageservice.service;

import com.fikaro.storageservice.dto.ProductDto;
import com.fikaro.storageservice.dto.ProductImagesDto;
import com.fikaro.storageservice.entity.ProductEtt;
import com.fikaro.storageservice.entity.ProductImageEtt;
import com.fikaro.storageservice.entity.SwiperImagesEtt;
import com.fikaro.storageservice.repository.ProductRepository;
import com.fikaro.storageservice.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts(){
        log.info("getAllProducts");
        List<ProductEtt> productEttList = productRepository.findAll();
        return productEttList.stream().map(this::mapModelToResponse).toList();
    }

    public String saveProduct(String name, int size, String description, List<MultipartFile> imageFiles) throws IOException {

        Optional<ProductEtt> existingProduct = productRepository.findByName(name);
        if(existingProduct.isPresent()){
            return "Error: The product with name " + name + " already exists!";
        }

        // Create ProductDto object
        ProductEtt productEtt = new ProductEtt();
        productEtt.setName(name);
        productEtt.setSize(size);
        productEtt.setDescription(description);

        // Convert MultipartFile objects to ProductImagesDto
        List<ProductImageEtt> productImageEtts = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            ProductImageEtt productImageEtt = new ProductImageEtt();
            productImageEtt.setName(imageFile.getOriginalFilename());
            productImageEtt.setType(imageFile.getContentType());
            productImageEtt.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
            productImageEtt.setProduct(productEtt);
            productImageEtts.add(productImageEtt);
        }


        productEtt.setImages(productImageEtts);

        productRepository.save(productEtt);

        return "file uploaded successfully!";
    }

    public boolean deleteImageById(Long id){

        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {

            log.error("Product with id {} not found", id);
        }

        return false;
    }

    private ProductDto mapModelToResponse(ProductEtt productEtt){
        return ProductDto.builder()
                .id(productEtt.getId())
                .name(productEtt.getName())
                .size(productEtt.getSize())
                .description(productEtt.getDescription())
                .build();
    }
}
