package com.itapia.gestion_usuarios.repository;

import com.itapia.gestion_usuarios.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
}
