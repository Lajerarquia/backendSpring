package com.itapia.gestion_usuarios.service.impl;

import com.itapia.gestion_usuarios.dto.UsuarioCreateDTO;
import com.itapia.gestion_usuarios.dto.UsuarioDTO;
import com.itapia.gestion_usuarios.entity.RolEntity;
import com.itapia.gestion_usuarios.entity.UsuarioEntity;
import com.itapia.gestion_usuarios.repository.RolRepository;
import com.itapia.gestion_usuarios.repository.UsuarioRepository;
import com.itapia.gestion_usuarios.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              RolRepository rolRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UsuarioDTO toDTO(UsuarioEntity e) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(e.getId());
        dto.setUsername(e.getUsername());
        dto.setNombre(e.getNombre());
        dto.setRoles(e.getRoles().stream().map(RolEntity::getNombre).collect(Collectors.toSet()));
        return dto;
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username ya existe");
        }
        UsuarioEntity u = new UsuarioEntity();
        u.setUsername(dto.getUsername());
        u.setNombre(dto.getNombre());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));

        // roles
        Set<RolEntity> roles = new HashSet<>();
        if (dto.getRoles() != null) {
            for (String rname : dto.getRoles()) {
                RolEntity r = rolRepository.findByNombre(rname)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rname));
                roles.add(r);
            }
        }
        u.setRoles(roles);
        UsuarioEntity saved = usuarioRepository.save(u);
        return toDTO(saved);
    }

    @Override
    public UsuarioDTO obtenerPorId(Long id) {
        UsuarioEntity u = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toDTO(u);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioCreateDTO dto) {
        UsuarioEntity u = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setNombre(dto.getNombre());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        // actualizar roles si vienen
        if (dto.getRoles() != null) {
            Set<RolEntity> roles = dto.getRoles().stream()
                    .map(rname -> rolRepository.findByNombre(rname).orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rname)))
                    .collect(Collectors.toSet());
            u.setRoles(roles);
        }
        UsuarioEntity saved = usuarioRepository.save(u);
        return toDTO(saved);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
