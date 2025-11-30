package com.itapia.gestion_usuarios.config;

import com.itapia.gestion_usuarios.entity.PermisoEntity;
import com.itapia.gestion_usuarios.entity.RolEntity;
import com.itapia.gestion_usuarios.entity.UsuarioEntity;
import com.itapia.gestion_usuarios.repository.PermisoRepository;
import com.itapia.gestion_usuarios.repository.RolRepository;
import com.itapia.gestion_usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(PermisoRepository permisoRepo,
                               RolRepository rolRepo,
                               UsuarioRepository usuarioRepo,
                               PasswordEncoder passwordEncoder) {

        return args -> {

            // Crear permisos
            String[] permisos = {
                    "USUARIO_CREATE",
                    "USUARIO_READ",
                    "USUARIO_UPDATE",
                    "USUARIO_DELETE"
            };

            Set<PermisoEntity> permisosSet = new HashSet<>();
            for (String nombre : permisos) {
                PermisoEntity p = permisoRepo.findByNombre(nombre)
                        .orElseGet(() -> permisoRepo.save(new PermisoEntity(nombre)));
                permisosSet.add(p);
            }

            // Crear rol ADMIN
            RolEntity adminRole = rolRepo.findByNombre("ROLE_ADMIN")
                    .orElseGet(() -> {
                        RolEntity r = new RolEntity();
                        r.setNombre("ROLE_ADMIN");
                        r.setPermisos(permisosSet);
                        return rolRepo.save(r);
                    });

           
            RolEntity userRole = rolRepo.findByNombre("ROLE_USER")
                    .orElseGet(() -> {
                        RolEntity r = new RolEntity();
                        r.setNombre("ROLE_USER");
                        r.setPermisos(permisosSet.stream()
                                .filter(p -> p.getNombre().equals("USUARIO_READ"))
                                .collect(HashSet::new, HashSet::add, HashSet::addAll));
                        return rolRepo.save(r);
                    });

            // Crear usuario admin
            if (usuarioRepo.findByUsername("admin").isEmpty()) {
                UsuarioEntity admin = new UsuarioEntity();
                admin.setUsername("admin");
                admin.setNombre("Administrador");
                admin.setPassword(passwordEncoder.encode("admin123")); 
                admin.setRoles(Set.of(adminRole));
                usuarioRepo.save(admin);
            }
        };
    }
}
