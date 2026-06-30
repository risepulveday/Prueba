package com.Oferta.oferta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.Oferta.oferta.dto.JuegoDTO;

// ⚠️ Cambia la URL si tu microservicio de juegos corre en otro puerto
@FeignClient(name = "microservicio-juegos", url = "http://localhost:8084")
public interface JuegoFeignClient {

    // Debe coincidir exactamente con el GET por ID del controlador de Juegos
    @GetMapping("/api/juegos/{id}")
    JuegoDTO obtenerJuegoPorId(@PathVariable("id") Long id);
}