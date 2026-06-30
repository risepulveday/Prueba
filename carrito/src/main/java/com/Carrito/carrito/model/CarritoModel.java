package com.Carrito.carrito.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrito")
@Data // Lombok genera automáticamente el método getItems() y setItems()
public class CarritoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long idCarrito;

    @NotNull(message = "El id de usuario es obligatorio")
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotNull(message = "El precio total es obligatorio")
    @Column(name = "precio_total")
    private Integer precioTotal;

    // Relación bidireccional con los ítems del carrito
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // Controla la serialización del JSON impidiendo el bucle infinito junto a
                          // @JsonBackReference
    private List<CarritoItemModel> items = new ArrayList<>();
}