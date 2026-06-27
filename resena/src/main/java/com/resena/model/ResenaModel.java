package com.resena.model;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "resena")
@Data
public class ResenaModel {
    // Generacion de id para tabla factura en long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena")
    private Long idResena;

    // Generacion del total para tabla factura en integer
    @NotNull(message = "La calificacion es obligatoria")
    @Column(name = "calificacion")
    private Integer calificacion;

    // Generacion de fecha para tabla factura en Date
    @NotNull(message = "La fecha es automatica")
    @Column(name = "fecha")
    private Date fecha;

    // Generacion del estado de orden para tabla factura en boolean
    @Column(name = "comentario")
    private String comentario;

    // Guardamos solo el ID para consultar el microservicio de Pagos vía Feign
    @NotNull(message = "usuario automatico")
    @Column(name = "id_usuario")
    private Long idUsuario;
}