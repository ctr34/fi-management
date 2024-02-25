package com.fikaro.storageservice.controller;

import com.fikaro.storageservice.dto.SwiperImagesDto;
import com.fikaro.storageservice.service.SwiperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/swiper")
@AllArgsConstructor
@Slf4j
public class SwiperController {

    private final SwiperService swiperService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SwiperImagesDto> getAllSwiper(){
        return swiperService.getAllSwiper();
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = swiperService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @PostMapping("/swapOrder/{entryId}/{moveDir}")
    @ResponseStatus(HttpStatus.OK)
    public void swapOrder(@PathVariable Long entryId, @PathVariable int moveDir) {
        swiperService.swapOrder(entryId, moveDir);
    }
}
