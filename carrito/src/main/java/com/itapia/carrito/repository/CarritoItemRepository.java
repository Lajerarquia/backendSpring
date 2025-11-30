package com.itapia.carrito.repository;

import com.itapia.carrito.entity.CarritoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarritoItemRepository extends JpaRepository<CarritoItemEntity, Long> {
    List<CarritoItemEntity> findByCarritoId(Long carritoId);
}
