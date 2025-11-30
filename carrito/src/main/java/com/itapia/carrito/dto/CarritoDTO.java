package com.itapia.carrito.dto;

import java.util.Set;

public class CarritoDTO {
    private Long id;
    private String usuario;
    private Set<CarritoItemDTO> items;

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public Set<CarritoItemDTO> getItems() { return items; }
    public void setItems(Set<CarritoItemDTO> items) { this.items = items; }
}
