package com.fikaro.storageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwiperImagesDto {
    private Long id;
    private String name;
    private String type;
    private byte[] imageData;
    private int displayOrder;
}
