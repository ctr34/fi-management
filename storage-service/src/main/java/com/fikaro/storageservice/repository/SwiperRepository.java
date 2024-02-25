package com.fikaro.storageservice.repository;

import com.fikaro.storageservice.entity.SwiperImagesEtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SwiperRepository extends JpaRepository<SwiperImagesEtt, Long> {
    Optional<SwiperImagesEtt> findByDisplayOrder(int displayOrder);

    @Query("SELECT COUNT(e) FROM SwiperImagesEtt e")
    int customCount(); // Use int as the return type
}
