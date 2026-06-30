package com.Dispositivo.dispositivo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.Dispositivo.dispositivo.dto.UsuarioDTO;

// Sustituye la URL por la de tu microservicio de usuarios local o su nombre en Eureka
@FeignClient(name = "usuario-service", url = "http://localhost:8081/api/usuario")
public interface UsuarioFeignClient {

    // Método espejo del GET /api/usuario/{id} del microservicio de Usuarios
    @GetMapping("/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long id);
}