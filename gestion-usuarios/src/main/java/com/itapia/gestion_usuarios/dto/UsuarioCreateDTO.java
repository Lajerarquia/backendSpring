package com.itapia.gestion_usuarios.dto;

import java.util.Set;

public class UsuarioCreateDTO {
    private String username;
    private String password;
    private String nombre;
    private Set<String> roles; 

    // getters y setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
