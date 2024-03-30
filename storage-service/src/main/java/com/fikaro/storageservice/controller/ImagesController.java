package com.fikaro.storageservice.controller;

import com.fikaro.storageservice.dto.ProductImagesDto;
import com.fikaro.storageservice.service.ImageService;
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
@RequestMapping("/api/images")
@AllArgsConstructor
@Slf4j
public class ImagesController {
    private final ImageService imageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductImagesDto> getAllProduct(){
        log.info("hit getAllProduct");
        return imageService.getALlImages();
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        log.info("hit downloadImage");
        byte[] imageData=imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<?> deleteImageById(Long id) {
        boolean deleted = imageService.deleteImageById(id);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }


}
