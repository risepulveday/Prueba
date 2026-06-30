package com.Juego.juego.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "categoria")
@Data
public class CategoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @NotNull(message = "El nombre de la categoría es obligatorio")
    @Column(name = "nombre_categoria")
    private String nombreCategoria;
}