package com.Carrito.carrito.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Carrito.carrito.model.CarritoModel;
import com.Carrito.carrito.service.CarritoService;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    // Inyección por constructor limpia y libre de advertencias
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // 1. Crear un nuevo carrito (Calculando automáticamente el precio con Feign)
    @PostMapping
    public ResponseEntity<CarritoModel> crearCarrito(@Valid @RequestBody CarritoModel carrito) {
        CarritoModel nuevoCarrito = carritoService.guardarYCalcularCarrito(carrito);
        return new ResponseEntity<>(nuevoCarrito, HttpStatus.CREATED);
    }

    // 2. Obtener todos los carritos registrados (Formato plano sin HATEOAS)
    @GetMapping
    public ResponseEntity<List<CarritoModel>> obtenerTodos() {
        List<CarritoModel> carritos = carritoService.obtenerTodas();
        return ResponseEntity.ok(carritos);
    }

    // 3. Obtener un carrito específico por su ID (¡Invocado por Feign desde
    // Facturas!)
    @GetMapping("/{id}")
    public ResponseEntity<CarritoModel> obtenerPorId(@PathVariable Long id) {
        return carritoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Obtener el carrito activo asignado a un ID de usuario específico
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CarritoModel> obtenerPorIdUsuario(@PathVariable Long idUsuario) {
        return carritoService.obtenerPorIdUsuario(idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar un carrito y todos sus ítems asociados (en cascada automática)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        try {
            carritoService.eliminarCarrito(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}