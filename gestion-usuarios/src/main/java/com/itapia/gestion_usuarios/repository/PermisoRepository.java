package com.itapia.gestion_usuarios.repository;

import com.itapia.gestion_usuarios.entity.PermisoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PermisoRepository extends JpaRepository<PermisoEntity, Long> {
    Optional<PermisoEntity> findByNombre(String nombre);
}
