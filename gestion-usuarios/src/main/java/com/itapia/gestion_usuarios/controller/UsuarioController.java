package com.itapia.gestion_usuarios.controller;

import com.itapia.gestion_usuarios.dto.UsuarioCreateDTO;
import com.itapia.gestion_usuarios.dto.UsuarioDTO;
import com.itapia.gestion_usuarios.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USUARIO_CREATE')")
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioCreateDTO dto) {
        UsuarioDTO creado = usuarioService.crearUsuario(dto);
        return ResponseEntity.ok(creado);
    }

    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USUARIO_READ')")
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USUARIO_READ')")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USUARIO_UPDATE')")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USUARIO_DELETE')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
