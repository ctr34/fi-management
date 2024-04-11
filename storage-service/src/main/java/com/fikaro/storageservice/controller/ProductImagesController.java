package com.fikaro.storageservice.controller;

import com.fikaro.storageservice.dto.ProductImagesDto;
import com.fikaro.storageservice.service.ProductImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productImages")
@AllArgsConstructor
@Slf4j
public class ProductImagesController {
    private final ProductImageService productImageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductImagesDto> getAllImages(){
        log.info("hit getAllProduct");
        return productImageService.getALlImages();
    }

    @GetMapping("getImageIds/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> getImagesIdByProductId(@PathVariable Long id){
        return productImageService.getImagesIdByProductId(id);
    }

    @PostMapping("/byId")
    public List<ProductImagesDto> getAllImagesById(@RequestParam("product_id") Long id){
        return productImageService.getALlImagesById(id);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadProductImage(@RequestParam("file") MultipartFile file) throws IOException {
        Long uploadImageId = productImageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImageId);
    }

    @GetMapping("getFirstImage/{id}")
    public ResponseEntity<?> getFirstImage(@PathVariable Long id){
        byte[] imageData= productImageService.getFirstImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping("getImageById/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id){
        byte[] imageData= productImageService.getImageById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<?> deleteImageById(Long id) {
        boolean deleted = productImageService.deleteImageById(id);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }


}
