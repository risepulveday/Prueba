package com.Juego.juego.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Juego.juego.dto.PagoDTO;

// 🔌 Conexión directa al nuevo microservicio independiente de Pagos
@FeignClient(name = "pago-service", url = "http://localhost:8085")
public interface PagoFeignClient {

    @GetMapping("/api/pagos/{id}")
    PagoDTO obtenerPagoPorId(@PathVariable("id") Long id);
}