package com.fikaro.storageservice.controller;

import com.fikaro.storageservice.dto.ProductImagesDto;
import com.fikaro.storageservice.entity.ProductImageEtt;
import com.fikaro.storageservice.service.ProductImageService;
import com.fikaro.storageservice.util.ImageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
        return productImageService.getALlImages();
    }

    @PostMapping("/uploadImageWithProductId")
    public ResponseEntity<?> uploadImageWithProductId(@RequestParam("productId") Long productId,
                                         @RequestParam("image") MultipartFile file) throws IOException {

        Long imageId = productImageService.uploadImageWithProductId(productId, file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(imageId);
    }

    @GetMapping("/getImagesIdByProductId/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> getImagesIdByProductId(@PathVariable Long productId){
        return productImageService.getImagesIdByProductId(productId);
    }

    @PostMapping("/byId")
    public List<ProductImagesDto> getAllImagesById(@RequestParam("product_id") Long id){
        return productImageService.getALlImagesById(id);
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

    @GetMapping("/getNumImagesByProductId")
    public int getNumImagesByProductId(@RequestParam("product_id") Long productId){

        return productImageService.getNumImagesByProductId(productId);
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
