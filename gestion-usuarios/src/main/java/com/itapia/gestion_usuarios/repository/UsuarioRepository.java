package com.itapia.gestion_usuarios.repository;

import com.itapia.gestion_usuarios.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
