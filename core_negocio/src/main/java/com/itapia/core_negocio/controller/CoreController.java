package com.itapia.core_negocio.controller;

import com.itapia.core_negocio.dto.CategoriaDTO;
import com.itapia.core_negocio.dto.ProductoDTO;
import com.itapia.core_negocio.service.CoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/core")
public class CoreController {

    private final CoreService coreService;

    public CoreController(CoreService coreService) {
        this.coreService = coreService;
    }

    // Categorias
    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(coreService.crearCategoria(dto));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(coreService.listarCategorias());
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(coreService.obtenerCategoriaPorId(id));
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(coreService.actualizarCategoria(id, dto));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        coreService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    // Productos
    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(coreService.crearProducto(dto));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        return ResponseEntity.ok(coreService.listarProductos());
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(coreService.obtenerProductoPorId(id));
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(coreService.actualizarProducto(id, dto));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        coreService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
