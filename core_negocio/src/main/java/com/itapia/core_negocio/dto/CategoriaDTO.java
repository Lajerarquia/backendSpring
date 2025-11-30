package com.itapia.core_negocio.dto;

import java.util.Set;

public class CategoriaDTO {
    private Long id;
    private String nombre;
    private Set<ProductoDTO> productos;

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<ProductoDTO> getProductos() { return productos; }
    public void setProductos(Set<ProductoDTO> productos) { this.productos = productos; }
}
