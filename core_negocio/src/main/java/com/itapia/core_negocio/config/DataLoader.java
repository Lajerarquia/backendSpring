package com.itapia.core_negocio.config;

import com.itapia.core_negocio.entity.CategoriaEntity;
import com.itapia.core_negocio.entity.ProductoEntity;
import com.itapia.core_negocio.repository.CategoriaRepository;
import com.itapia.core_negocio.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CategoriaRepository categoriaRepo,
                               ProductoRepository productoRepo) {
        return args -> {
            if(categoriaRepo.count() == 0) {
                CategoriaEntity cat1 = new CategoriaEntity();
                cat1.setNombre("Juegos de Mesa");
                categoriaRepo.save(cat1);

                ProductoEntity prod1 = new ProductoEntity();
                prod1.setNombre("Catan");
                prod1.setPrecio(35000.0);
                prod1.setCategoria(cat1);
                productoRepo.save(prod1);

                ProductoEntity prod2 = new ProductoEntity();
                prod2.setNombre("Monopoly");
                prod2.setPrecio(25000.0);
                prod2.setCategoria(cat1);
                productoRepo.save(prod2);
            }
        };
    }
}
