package com.Pago.pago.model;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "pagos")
@Data
public class PagoModel {
    // Generacion de id para tabla factura en long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    // Generacion de fecha para tabla factura en Date
    @NotNull(message = "La fecha es automatica")
    @Column(name = "fecha_pago")
    private Date fechaPago;

    // Generacion del estado de orden para tabla factura en boolean
    @NotNull(message = "Estado de Pago")
    @Column(name = "Estado_pago")
    private boolean estadoPago;

    // Generacion del total para tabla factura en integer
    @NotNull(message = "metodo de pago es obligatorio")
    @Column(name = "metodo_Pago")
    private Integer metodoPago;

    // Guardamos solo el ID para consultar el microservicio de Pagos vía Feign
    @NotNull(message = "El id de pago es obligatorio")
    @Column(name = "id_factura")
    private Long idFactura;
}