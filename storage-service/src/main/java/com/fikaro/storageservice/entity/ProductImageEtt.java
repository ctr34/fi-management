package com.fikaro.storageservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_product_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageEtt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEtt product;

    @Lob
    @Column(name = "imageData",length = 1048576) //1MB
    private byte[] imageData;
}
