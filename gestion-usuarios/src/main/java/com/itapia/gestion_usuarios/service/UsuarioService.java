package com.itapia.gestion_usuarios.service;

import com.itapia.gestion_usuarios.dto.UsuarioDTO;
import com.itapia.gestion_usuarios.dto.UsuarioCreateDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioDTO crearUsuario(UsuarioCreateDTO dto);
    UsuarioDTO obtenerPorId(Long id);
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO actualizarUsuario(Long id, UsuarioCreateDTO dto);
    void eliminarUsuario(Long id);
}
