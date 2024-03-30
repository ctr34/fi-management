package com.fikaro.storageservice.repository;

import com.fikaro.storageservice.entity.ProductEtt;
import com.fikaro.storageservice.entity.SwiperImagesEtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEtt, Long> {

    Optional<ProductEtt> findByName(String filename);

    @Query("SELECT COUNT(e) FROM ProductEtt e")
    int customCount(); // Use int as the return type

}
