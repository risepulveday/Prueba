package com.Factura.factura.dto;

import lombok.Data;
import com.Factura.factura.model.FacturaModel;

@Data
public class FacturaCompletaDTO {
    private FacturaModel factura;
    private UsuarioDTO usuario;
    private CarritoDTO carrito;
}