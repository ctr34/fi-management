package com.fikaro.storageservice.repository;

import com.fikaro.storageservice.entity.ProductImageEtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImageEtt, Long> {

    List<ProductImageEtt> findByProduct_Id(Long productId);

    void deleteByProduct_Id(Long productId);

    @Query("SELECT e.id FROM ProductImageEtt e WHERE e.product.id=:productId")
    List<Long> findImageIds(Long productId);

    @Query("SELECT COUNT(e) FROM ProductImageEtt e WHERE e.product.id=:productId")
    int countByProductId(Long productId);
}
