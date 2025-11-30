package com.itapia.core_negocio.service;

import com.itapia.core_negocio.dto.CategoriaDTO;
import com.itapia.core_negocio.dto.ProductoDTO;
import java.util.List;

public interface CoreService {
    CategoriaDTO crearCategoria(CategoriaDTO dto);
    CategoriaDTO obtenerCategoriaPorId(Long id);
    List<CategoriaDTO> listarCategorias();
    CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto);
    void eliminarCategoria(Long id);

    ProductoDTO crearProducto(ProductoDTO dto);
    ProductoDTO obtenerProductoPorId(Long id);
    List<ProductoDTO> listarProductos();
    ProductoDTO actualizarProducto(Long id, ProductoDTO dto);
    void eliminarProducto(Long id);
}
