package com.itapia.carrito.dto;

public class CarritoItemDTO {
    private Long id;
    private String producto;
    private Double precio;
    private Integer cantidad;
    private Long carritoId; 

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Long getCarritoId() { return carritoId; }
    public void setCarritoId(Long carritoId) { this.carritoId = carritoId; }
}
