package com.fikaro.storageservice.repository;

import com.fikaro.storageservice.entity.ProductImageEtt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ProductImageEtt, Long> {

    Optional<ProductImageEtt> findByName(String filename);
}
