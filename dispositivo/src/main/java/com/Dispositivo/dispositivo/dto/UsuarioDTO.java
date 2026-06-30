package com.Dispositivo.dispositivo.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String email;
    // 💡 No incluimos contraseñas, roles ni datos sensibles del usuario.
}