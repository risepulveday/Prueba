package com.Factura.factura.model;

import java.sql.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "factura")
@Data
public class FacturaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @NotNull(message = "El total es obligatorio")
    @Column(name = "total")
    private Integer total;

    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha")
    private Date fecha;

    @NotNull(message = "El estado de orden es obligatorio")
    @Column(name = "estado_orden")
    private boolean estadoOrden;

    // 👤 Llave lógica externa conectada al Microservicio de Usuarios
    // (Puerto 8080)
    @NotNull(message = "El id de usuario es obligatorio")
    @Column(name = "id_usuario")
    private Long idUsuario;

    // 🛒 Llave lógica externa conectada al Microservicio de Carrito
    // (Puerto 8082)
    @NotNull(message = "El id de carrito es obligatorio")
    @Column(name = "id_carrito")
    private Long idCarrito;

    // 💳 Llave lógica externa conectada al Microservicio de Pagos
    // (Puerto 8085)
    @NotNull(message = "El id de pago es obligatorio")
    @Column(name = "id_pago")
    private Long idPago;
}