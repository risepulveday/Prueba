package com.Carrito.carrito.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "carrito_item")
@Data
public class CarritoItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long idItem;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    @Column(name = "cantidad")
    private Integer cantidad;

    // 🎮 Conectado con el microservicio de Juegos según tu diagrama
    @NotNull(message = "El id de juego es obligatorio")
    @Column(name = "id_juego")
    private Long idJuego;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito")
    @JsonBackReference
    @ToString.Exclude
    private CarritoModel carrito;
}