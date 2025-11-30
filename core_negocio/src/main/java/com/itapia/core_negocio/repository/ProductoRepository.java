package com.itapia.core_negocio.repository;

import com.itapia.core_negocio.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    List<ProductoEntity> findByCategoriaId(Long categoriaId);
}
