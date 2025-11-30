package com.itapia.carrito.repository;

import com.itapia.carrito.entity.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {
    Optional<CarritoEntity> findByUsuario(String usuario);
}
