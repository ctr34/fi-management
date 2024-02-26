package com.fikaro.storageservice.service;

import com.fikaro.storageservice.dto.SwiperImagesDto;
import com.fikaro.storageservice.entity.ImageData;
import com.fikaro.storageservice.entity.SwiperImagesEtt;
import com.fikaro.storageservice.repository.SwiperRepository;
import com.fikaro.storageservice.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SwiperService {
    @Autowired
    private SwiperRepository swiperRepository;

    public List<SwiperImagesDto> getAllSwiperByOrder(){
        List<SwiperImagesEtt> swiperImagesList = swiperRepository.findAllByOrderByDisplayOrderAsc();
        return swiperImagesList.stream().map(this::mapModelToResponse).toList();

    }

    public String uploadImage(MultipartFile file) throws IOException {

        int amount = swiperRepository.customCount();

        swiperRepository.save(SwiperImagesEtt.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .displayOrder(amount+1)
                .build());

        return "file uploaded successfully : " + file.getOriginalFilename();
    }

    public byte[] downloadImage(String fileName){
        Optional<SwiperImagesEtt> dbImage = swiperRepository.findByName(fileName);
        return ImageUtils.decompressImage(dbImage.get().getImageData());
    }

    public void swapOrder(Long entryId, int moveDir){
        SwiperImagesEtt entry = swiperRepository.findById(entryId).orElse(null);

        if (entry != null) {
            int currentDisplayOrder = entry.getDisplayOrder();

            // Find the other entry with the adjacent displayOrder
            SwiperImagesEtt otherEntry = swiperRepository.findByDisplayOrder(currentDisplayOrder + moveDir)
                    .orElse(null);

            if (otherEntry != null) {
                // Swap displayOrder values
                entry.setDisplayOrder(currentDisplayOrder + moveDir);
                otherEntry.setDisplayOrder(currentDisplayOrder);

                // Save the updated entries
                swiperRepository.save(entry);
                swiperRepository.save(otherEntry);

                log.info("Swapped displayOrder for entries with IDs {} and {}", entryId, otherEntry.getId());
            } else {
                log.warn("Could not find adjacent entry for ID {} with displayOrder {}", entryId, currentDisplayOrder + moveDir);
            }
        } else {
            log.warn("Could not find entry with ID {}", entryId);
        }
    }

    private SwiperImagesDto mapModelToResponse(SwiperImagesEtt swiperImagesEtt){
        return SwiperImagesDto.builder()
                .id(swiperImagesEtt.getId())
                .name(swiperImagesEtt.getName())
                .type(swiperImagesEtt.getType())
                .imageData(swiperImagesEtt.getImageData())
                .displayOrder(swiperImagesEtt.getDisplayOrder())
                .build();
    }
}
