package com.itapia.core_negocio.repository;

import com.itapia.core_negocio.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    Optional<CategoriaEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
