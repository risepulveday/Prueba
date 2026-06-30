package com.resena.client;

import com.resena.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name: Debe ser igual al spring.application.name configurado en el servicio de usuarios
// url: Apunta a la ubicación física de ejecución del microservicio remoto (Ej: puerto 8081)
@FeignClient(name = "user-service", url = "http://localhost:8081/api/usuarios")
public interface UsuarioFeignClient {

    // Enlaza de manera directa con el método buscarPorId del UsuarioController
    @GetMapping("/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long id);
}