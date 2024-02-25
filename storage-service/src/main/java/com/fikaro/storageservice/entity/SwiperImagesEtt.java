package com.fikaro.storageservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_swiper_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwiperImagesEtt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @Lob
    @Column(name = "imageData",length = 1048576) //1MB
    private byte[] imageData;
    private int displayOrder;
}
