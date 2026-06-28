package com.Oferta.oferta.model;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "oferta")
@Data
public class OfertaModel {
    //Generacion de id para tabla oferta en long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta")
    private Long idOferta;

    //Generacion de la oferta en integer
    @NotNull(message = "El descuento es obligatorio")
    @Column(name = "descuento")
    private Integer descuento;

    // Guardamos solo el ID para consultar el microservicio de Pagos vía Feign
    @NotNull(message = "El id de juego es obligatorio")
    @Column(name = "id_juego")
    private Long idJuego;
}