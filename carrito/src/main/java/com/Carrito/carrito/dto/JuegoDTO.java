package com.Carrito.carrito.dto;

import lombok.Data;

@Data
public class JuegoDTO {
    private Long idJuego;
    private String nombreJuego;
    private Integer precioJuego; // Fundamental para calcular el precio total del carrito
    private Integer stock;
}