package com.fikaro.storageservice.repository;

import com.fikaro.storageservice.entity.ProductImageEtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImageEtt, Long> {

    Optional<ProductImageEtt> findByName(String filename);

    List<ProductImageEtt> findByProduct_Id(Long productId);

    @Query("SELECT e.id FROM ProductImageEtt e")
    List<Long> findImageIds(Long id);
}
