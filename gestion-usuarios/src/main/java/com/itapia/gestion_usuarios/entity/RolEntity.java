package com.itapia.gestion_usuarios.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String nombre; 

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "roles_permisos",
      joinColumns = @JoinColumn(name = "rol_id"),
      inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private Set<PermisoEntity> permisos = new HashSet<>();

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<PermisoEntity> getPermisos() { return permisos; }
    public void setPermisos(Set<PermisoEntity> permisos) { this.permisos = permisos; }
}
