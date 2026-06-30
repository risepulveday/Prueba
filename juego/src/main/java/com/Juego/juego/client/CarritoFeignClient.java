package com.Juego.juego.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Juego.juego.dto.CarritoDTO;

@FeignClient(name = "carrito-service", url = "http://localhost:8082") // Supongamos que Carrito usa el 8082
public interface CarritoFeignClient {

    @GetMapping("/api/carritos/{id}")
    CarritoDTO obtenerCarritoPorId(@PathVariable("id") Long id);
}