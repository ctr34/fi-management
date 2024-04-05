package com.fikaro.storageservice.repository;

import com.fikaro.storageservice.entity.ProductImageEtt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImageEtt, Long> {

    Optional<ProductImageEtt> findByName(String filename);

    List<ProductImageEtt> findByProduct_Id(Long productId);
}
