package com.Juego.juego.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Juego.juego.dto.UsuarioDTO;

@FeignClient(name = "usuario-service", url = "http://localhost:8080") // Puerto de Usuarios
public interface UsuarioFeignClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long id);
}