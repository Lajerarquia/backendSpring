package com.itapia.carrito.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carrito_items")
public class CarritoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String producto;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id")
    private CarritoEntity carrito;

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public CarritoEntity getCarrito() { return carrito; }
    public void setCarrito(CarritoEntity carrito) { this.carrito = carrito; }
}
