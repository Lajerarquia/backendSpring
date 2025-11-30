package com.itapia.carrito.service;

import com.itapia.carrito.dto.CarritoDTO;
import com.itapia.carrito.dto.CarritoItemDTO;
import java.util.List;

public interface CarritoService {
    CarritoDTO crearCarrito(CarritoDTO dto);
    CarritoDTO obtenerCarritoPorId(Long id);
    List<CarritoDTO> listarCarritos();
    CarritoDTO actualizarCarrito(Long id, CarritoDTO dto);
    void eliminarCarrito(Long id);

    CarritoItemDTO agregarItem(CarritoItemDTO dto);
    CarritoItemDTO actualizarItem(Long id, CarritoItemDTO dto);
    void eliminarItem(Long id);
}
