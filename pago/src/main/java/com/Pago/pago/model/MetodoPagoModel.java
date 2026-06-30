package com.Pago.pago.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "metodo_pago")
@Data
public class MetodoPagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    private Long idMetodoPago;

    @NotNull(message = "El nombre del método de pago es obligatorio")
    @Column(name = "nombre_metodo_pago")
    private String nombreMetodoPago;
}