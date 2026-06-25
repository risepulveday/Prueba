package com.User.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "tipo_usuario")
@Data
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_usuario")
    private Long idTipoUsuario;

    @NotBlank(message = "El nombre del tipo de usuario es obligatorio")
    @Column(name = "nombre_tipo_usuario")
    private String nombreTipoUsuario;
}