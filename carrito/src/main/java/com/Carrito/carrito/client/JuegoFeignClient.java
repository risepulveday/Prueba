package com.Carrito.carrito.client;

import com.Carrito.carrito.dto.JuegoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "juego-service", url = "http://localhost:8084")
public interface JuegoFeignClient {

    @GetMapping("/api/juegos/{id}")
    JuegoDTO obtenerJuegoPorId(@PathVariable("id") Long id);
}