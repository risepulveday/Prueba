package com.Juego.juego.dto;

import com.Juego.juego.model.JuegoModel;

import lombok.Data;

@Data
public class FacturaCompletaDTO {
    private JuegoModel factura;
    private UsuarioDTO usuario;
    private CarritoDTO carrito;
}