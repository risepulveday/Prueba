package com.Notificacion.notificacion.model;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "notificacion")
@Data
public class NotificacionModel {
    //Generacion de id para tabla notificacion en long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long idNotificacion;

    //Generacion de fecha para tabla notificacion en Date
    @NotNull(message = "La fecha es automatica")
    @Column(name = "fecha")
    private Date fecha;

    // Guardamos solo el ID para consultar el microservicio de Pagos vía Feign
    @NotNull(message = "El id de usuario es obligatorio")
    @Column(name = "id_usuario")
    private Long idUsuario;
}