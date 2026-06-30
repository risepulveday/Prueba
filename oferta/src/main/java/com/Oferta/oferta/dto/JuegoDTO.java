package com.Oferta.oferta.dto;

import lombok.Data;

@Data
public class JuegoDTO {
    private Long idJuego;
    private String nombreJuego;
    private Double precioJuego;
    private Integer stock;
}