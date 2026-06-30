package com.Juego.juego.dto;

import lombok.Data;

@Data
public class CarritoDTO {
    private Long idCarrito;
    private Long idUsuario;
    private Integer precioTotal;
}