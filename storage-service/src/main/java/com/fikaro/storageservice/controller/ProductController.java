package com.fikaro.storageservice.controller;

import com.fikaro.storageservice.dto.ProductDto;
import com.fikaro.storageservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<ProductDto> getAllProducts() {
//        return productService.getAllProducts();
//    }

    @PostMapping
    public ResponseEntity<?> addNewProduct(@RequestParam("name") String name,
                                           @RequestParam("size") int size,
                                           @RequestParam("description") String description,
                                           @RequestParam("images") List<MultipartFile> images) throws IOException {

        String addProduct = productService.saveProduct(name, size, description, images);

        if (addProduct.startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addProduct);
        }

        return  ResponseEntity.status(HttpStatus.OK)
                .body(addProduct);
    }

//    @GetMapping("/{fileName}")
//    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
//        log.info("hit downloadImage");
//        byte[] imageData=productService.downloadImage(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//    }
}
