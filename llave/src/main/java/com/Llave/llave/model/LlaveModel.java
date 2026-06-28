package com.Llave.llave.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "llave")
@Data
public class LlaveModel {
    // Generacion de id para tabla llave en long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_llave")
    private Long idLlave;

    // Guardamos solo el ID para consultar el microservicio de Pagos vía Feign
    @NotNull(message = "El id de juego es obligatorio")
    @Column(name = "id_juego")
    private Long idJuego;
}