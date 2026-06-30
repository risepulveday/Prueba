package com.Resena.resena.dto;

import com.Resena.resena.model.ResenaModel;
import lombok.Data;

@Data
public class ResenaConUsuarioDTO {
    private ResenaModel resena;
    private UsuarioDTO usuario; // El DTO que mapea los datos remotos del usuario
}