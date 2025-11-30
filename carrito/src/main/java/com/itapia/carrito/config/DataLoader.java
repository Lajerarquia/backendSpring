package com.itapia.carrito.config;

import com.itapia.carrito.entity.CarritoEntity;
import com.itapia.carrito.entity.CarritoItemEntity;
import com.itapia.carrito.repository.CarritoItemRepository;
import com.itapia.carrito.repository.CarritoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CarritoRepository carritoRepo,
                               CarritoItemRepository itemRepo) {
        return args -> {
            if(carritoRepo.count() == 0) {
                CarritoEntity carrito = new CarritoEntity();
                carrito.setUsuario("testuser");
                carritoRepo.save(carrito);

                CarritoItemEntity item1 = new CarritoItemEntity();
                item1.setProducto("Catan");
                item1.setPrecio(35000.0);
                item1.setCantidad(1);
                item1.setCarrito(carrito);
                itemRepo.save(item1);
            }
        };
    }
}
