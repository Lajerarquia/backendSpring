package com.itapia.carrito.service.impl;

import com.itapia.carrito.dto.CarritoDTO;
import com.itapia.carrito.dto.CarritoItemDTO;
import com.itapia.carrito.entity.CarritoEntity;
import com.itapia.carrito.entity.CarritoItemEntity;
import com.itapia.carrito.repository.CarritoItemRepository;
import com.itapia.carrito.repository.CarritoRepository;
import com.itapia.carrito.service.CarritoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository itemRepository;

    public CarritoServiceImpl(CarritoRepository carritoRepository, CarritoItemRepository itemRepository) {
        this.carritoRepository = carritoRepository;
        this.itemRepository = itemRepository;
    }

    //  Mapeo DTO 
    private CarritoDTO toDTO(CarritoEntity e) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(e.getId());
        dto.setUsuario(e.getUsuario());
        dto.setItems(e.getItems().stream().map(this::toDTO).collect(Collectors.toSet()));
        return dto;
    }

    private CarritoItemDTO toDTO(CarritoItemEntity e) {
        CarritoItemDTO dto = new CarritoItemDTO();
        dto.setId(e.getId());
        dto.setProducto(e.getProducto());
        dto.setPrecio(e.getPrecio());
        dto.setCantidad(e.getCantidad());
        dto.setCarritoId(e.getCarrito() != null ? e.getCarrito().getId() : null);
        return dto;
    }

    private CarritoEntity toEntity(CarritoDTO dto) {
        CarritoEntity e = new CarritoEntity();
        e.setUsuario(dto.getUsuario());
        return e;
    }

    private CarritoItemEntity toEntity(CarritoItemDTO dto) {
        CarritoItemEntity e = new CarritoItemEntity();
        e.setProducto(dto.getProducto());
        e.setPrecio(dto.getPrecio());
        e.setCantidad(dto.getCantidad());
        return e;
    }

    // Carrito
    @Override
    public CarritoDTO crearCarrito(CarritoDTO dto) {
        CarritoEntity saved = carritoRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public CarritoDTO obtenerCarritoPorId(Long id) {
        CarritoEntity e = carritoRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return toDTO(e);
    }

    @Override
    public List<CarritoDTO> listarCarritos() {
        return carritoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CarritoDTO actualizarCarrito(Long id, CarritoDTO dto) {
        CarritoEntity e = carritoRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        e.setUsuario(dto.getUsuario());
        CarritoEntity saved = carritoRepository.save(e);
        return toDTO(saved);
    }

    @Override
    public void eliminarCarrito(Long id) {
        carritoRepository.deleteById(id);
    }

    // Items
    @Override
    public CarritoItemDTO agregarItem(CarritoItemDTO dto) {
        CarritoItemEntity e = toEntity(dto);
        if (dto.getCarritoId() != null) {
            CarritoEntity carrito = carritoRepository.findById(dto.getCarritoId())
                    .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
            e.setCarrito(carrito);
        }
        CarritoItemEntity saved = itemRepository.save(e);
        return toDTO(saved);
    }

    @Override
    public CarritoItemDTO actualizarItem(Long id, CarritoItemDTO dto) {
        CarritoItemEntity e = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item no encontrado"));
        e.setProducto(dto.getProducto());
        e.setPrecio(dto.getPrecio());
        e.setCantidad(dto.getCantidad());
        if (dto.getCarritoId() != null) {
            CarritoEntity carrito = carritoRepository.findById(dto.getCarritoId())
                    .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
            e.setCarrito(carrito);
        }
        CarritoItemEntity saved = itemRepository.save(e);
        return toDTO(saved);
    }

    @Override
    public void eliminarItem(Long id) {
        itemRepository.deleteById(id);
    }
}
