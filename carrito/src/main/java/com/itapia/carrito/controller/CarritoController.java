package com.itapia.carrito.controller;

import com.itapia.carrito.dto.CarritoDTO;
import com.itapia.carrito.dto.CarritoItemDTO;
import com.itapia.carrito.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // Carritos
    @PostMapping
    public ResponseEntity<CarritoDTO> crearCarrito(@RequestBody CarritoDTO dto) {
        return ResponseEntity.ok(carritoService.crearCarrito(dto));
    }

    @GetMapping
    public ResponseEntity<List<CarritoDTO>> listarCarritos() {
        return ResponseEntity.ok(carritoService.listarCarritos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoDTO> obtenerCarrito(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.obtenerCarritoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarritoDTO> actualizarCarrito(@PathVariable Long id, @RequestBody CarritoDTO dto) {
        return ResponseEntity.ok(carritoService.actualizarCarrito(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    // Items 
    @PostMapping("/items")
    public ResponseEntity<CarritoItemDTO> agregarItem(@RequestBody CarritoItemDTO dto) {
        return ResponseEntity.ok(carritoService.agregarItem(dto));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<CarritoItemDTO> actualizarItem(@PathVariable Long id, @RequestBody CarritoItemDTO dto) {
        return ResponseEntity.ok(carritoService.actualizarItem(id, dto));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        carritoService.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}
