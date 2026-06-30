package com.Juego.juego.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Table(name = "juego")
@Data
public class JuegoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juego")
    private Long idJuego;

    @NotNull(message = "El nombre del juego es obligatorio")
    @Column(name = "nombre_juego")
    private String nombreJuego;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(name = "precio_juego")
    private Integer precioJuego;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock")
    private Integer stock;

    // 🏷️ Relación física local con Categoría
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_categoria")
    private CategoriaModel categoria;
}