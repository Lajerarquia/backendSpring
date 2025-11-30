package com.itapia.core_negocio.service.impl;

import com.itapia.core_negocio.dto.CategoriaDTO;
import com.itapia.core_negocio.dto.ProductoDTO;
import com.itapia.core_negocio.entity.CategoriaEntity;
import com.itapia.core_negocio.entity.ProductoEntity;
import com.itapia.core_negocio.repository.CategoriaRepository;
import com.itapia.core_negocio.repository.ProductoRepository;
import com.itapia.core_negocio.service.CoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoreServiceImpl implements CoreService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CoreServiceImpl(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    // Mapeo DTO 
    private CategoriaDTO toDTO(CategoriaEntity e) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setProductos(
            e.getProductos() != null ?
            e.getProductos().stream().map(this::toDTO).collect(Collectors.toSet()) :
            new HashSet<>()
        );
        return dto;
    }

    private ProductoDTO toDTO(ProductoEntity e) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setPrecio(e.getPrecio());
        dto.setCategoriaId(e.getCategoria() != null ? e.getCategoria().getId() : null);
        return dto;
    }

    private CategoriaEntity toEntity(CategoriaDTO dto) {
        CategoriaEntity e = new CategoriaEntity();
        e.setNombre(dto.getNombre());
        e.setProductos(new HashSet<>()); // inicializar colección para evitar NPE
        return e;
    }

    private ProductoEntity toEntity(ProductoDTO dto) {
        ProductoEntity e = new ProductoEntity();
        e.setNombre(dto.getNombre());
        e.setPrecio(dto.getPrecio());
        return e;
    }

    //Categoria 
    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        if (categoriaRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Categoria ya existe");
        }
        CategoriaEntity saved = categoriaRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        CategoriaEntity e = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return toDTO(e);
    }

    @Override
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto) {
        CategoriaEntity e = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        e.setNombre(dto.getNombre());
        CategoriaEntity saved = categoriaRepository.save(e);
        return toDTO(saved);
    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    // Producto
    @Override
    public ProductoDTO crearProducto(ProductoDTO dto) {
        ProductoEntity e = toEntity(dto);
        if (dto.getCategoriaId() != null) {
            CategoriaEntity cat = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
            e.setCategoria(cat);
        }
        ProductoEntity saved = productoRepository.save(e);
        return toDTO(saved);
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        ProductoEntity e = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toDTO(e);
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        ProductoEntity e = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        e.setNombre(dto.getNombre());
        e.setPrecio(dto.getPrecio());
        if (dto.getCategoriaId() != null) {
            CategoriaEntity cat = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
            e.setCategoria(cat);
        }
        ProductoEntity saved = productoRepository.save(e);
        return toDTO(saved);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
