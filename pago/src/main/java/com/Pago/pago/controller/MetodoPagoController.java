package com.Pago.pago.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Pago.pago.model.MetodoPagoModel;
import com.Pago.pago.service.MetodoPagoService;

import java.util.List;

@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    public MetodoPagoController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    // 1. Crear nuevo método de pago
    @PostMapping
    public ResponseEntity<MetodoPagoModel> crearMetodoPago(@Valid @RequestBody MetodoPagoModel metodoPago) {
        MetodoPagoModel nuevoMetodo = metodoPagoService.crearMetodoPago(metodoPago);
        return new ResponseEntity<>(nuevoMetodo, HttpStatus.CREATED);
    }

    // 2. Obtener el listado del catálogo
    @GetMapping
    public ResponseEntity<List<MetodoPagoModel>> obtenerTodos() {
        List<MetodoPagoModel> metodos = metodoPagoService.obtenerTodos();
        return ResponseEntity.ok(metodos);
    }

    // 3. Obtener un método por ID
    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagoModel> obtenerPorId(@PathVariable Long id) {
        return metodoPagoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Modificar un método de pago
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoModel> actualizarMetodoPago(@PathVariable Long id,
            @Valid @RequestBody MetodoPagoModel metodoPago) {
        try {
            MetodoPagoModel metodoActualizado = metodoPagoService.actualizarMetodoPago(id, metodoPago);
            return ResponseEntity.ok(metodoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar un método de pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMetodoPago(@PathVariable Long id) {
        try {
            metodoPagoService.eliminarMetodoPago(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}