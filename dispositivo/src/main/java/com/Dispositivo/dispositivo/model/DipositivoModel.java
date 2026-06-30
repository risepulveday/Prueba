package com.Dispositivo.dispositivo.model;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "dispositivo")
@Data
public class DispositivoModel {
    //Generacion de id para tabla dispositivo en long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispositivo")
    private Long idDispositivo;

    //Generacion del total para tabla dispositivo en integer
    @NotNull(message = "El total es obligatorio")
    @Column(name = "total")
    private Integer total;

    //Generacion de fecha para tabla dispositivo en Date
    @NotNull(message = "La fecha es automatica")
    @Column(name = "fecha")
    private Date fecha;

    //Generacion del estado de orden para tabla dispositivo en boolean
    @NotNull(message = "Estado de orden")
    @Column(name = "Estado_orden")
    private boolean estadoOrden;

    // Guardamos solo el ID para consultar el microservicio de Pagos vía Feign
    @NotNull(message = "El id de pago es obligatorio")
    @Column(name = "id_pago")
    private Long idPago;
}