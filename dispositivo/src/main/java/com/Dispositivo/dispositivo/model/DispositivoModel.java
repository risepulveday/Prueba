package com.Dispositivo.dispositivo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "dispositivo")
@Data
public class DispositivoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispositivo")
    private Long idDispositivo;

    @NotBlank(message = "El nombre del dispositivo es obligatorio")
    @Column(name = "nombre_dispositivo")
    private String nombreDispositivo;

    @NotBlank(message = "El procesador es obligatorio")
    @Column(name = "procesador")
    private String procesador;

    @NotNull(message = "El puntaje del procesador es obligatorio")
    @Column(name = "puntaje_procesador")
    private Integer puntajeProcesador;

    @NotBlank(message = "La tarjeta gráfica es obligatoria")
    @Column(name = "grafica")
    private String grafica;

    @NotNull(message = "El puntaje de la gráfica es obligatorio")
    @Column(name = "puntaje_grafica")
    private Integer puntajeGrafica;

    @NotNull(message = "La memoria RAM es obligatoria")
    @Column(name = "ram")
    private Integer ram;

    @NotNull(message = "El espacio en disco es obligatorio")
    @Column(name = "espacio")
    private Integer espacio;

    @NotNull(message = "El id de usuario es obligatorio")
    @Column(name = "id_usuario")
    private Long idUsuario;
}