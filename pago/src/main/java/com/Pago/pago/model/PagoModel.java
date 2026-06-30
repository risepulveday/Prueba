package com.Pago.pago.model;

import java.sql.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "pagos")
@Data
public class PagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @NotNull(message = "La fecha es automática")
    @Column(name = "fecha_pago")
    private Date fechaPago;

    @NotNull(message = "Estado de Pago")
    @Column(name = "Estado_pago")
    private boolean estadoPago;

    @NotNull(message = "El método de pago es obligatorio")
    @Column(name = "metodo_Pago")
    private Integer metodoPago;

    // 🔗 Llave foránea lógica microservicio de Facturas
    // (Puerto 8083)
    @NotNull(message = "El id de factura es obligatorio")
    @Column(name = "id_factura")
    private Long idFactura;
}