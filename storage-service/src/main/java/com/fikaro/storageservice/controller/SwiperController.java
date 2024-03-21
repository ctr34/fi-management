package com.fikaro.storageservice.controller;

import com.fikaro.storageservice.dto.SwiperImagesDto;
import com.fikaro.storageservice.service.SwiperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/swiper")
@AllArgsConstructor
@Slf4j
public class SwiperController {

    private final SwiperService swiperService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SwiperImagesDto> getAllSwiperByOrder(){
        return swiperService.getAllSwiperByOrder();
    }


    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") List<MultipartFile> files) throws IOException {

        List<String> resultList = new ArrayList<>();

        for (MultipartFile file : files ) {
            String uploadImage = swiperService.uploadImage(file);
            resultList.add(uploadImage);
        }

        if (resultList.stream().anyMatch(result -> result.startsWith("Error"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultList);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        log.info("hit downloadImage");
        byte[] imageData=swiperService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/swapOrder/{entryId}/{moveDir}")
    @ResponseStatus(HttpStatus.OK)
    public void swapOrder(@PathVariable Long entryId, @PathVariable int moveDir) {
        swiperService.swapOrder(entryId, moveDir);
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity<?> deleteImageById(Long id) {
        boolean deleted = swiperService.deleteImageById(id);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }
}
