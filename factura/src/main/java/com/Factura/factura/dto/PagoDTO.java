package com.Factura.factura.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PagoDTO {
    private Long idPago;
    private Date fechaPago;
    private Boolean estadoPago;
    private MetodoPagoDTO metodoPago; // Incluye el método de pago anidado

    @Data
    public static class MetodoPagoDTO {
        private Long idMetodoPago;
        private String nombreMetodoPago;
    }
}