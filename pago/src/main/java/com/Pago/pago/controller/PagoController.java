package com.Pago.pago.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Pago.pago.model.PagoModel;
import com.Pago.pago.service.PagoService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    private final PagoService pagoService;

    PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // 1. Crear una nueva pago
    @PostMapping
    public ResponseEntity<PagoModel> crearPago(@Valid @RequestBody PagoModel pago) {
        PagoModel nuevaPago = pagoService.crearPago(pago);
        return new ResponseEntity<>(nuevaPago, HttpStatus.CREATED);
    }

    // 2. Obtener todas las pagos
    @GetMapping
    public ResponseEntity<List<PagoModel>> obtenerTodas() {
        List<PagoModel> pagos = pagoService.obtenerTodas();
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    // 3. Obtener una pago por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagoModel> obtenerPorId(@PathVariable Long id) {
        return pagoService.obtenerPorId(id)
                .map(pago -> new ResponseEntity<>(pago, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar una pago existente
    @PutMapping("/{id}")
    public ResponseEntity<PagoModel> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoModel pago) {
        try {
            PagoModel pagoActualizada = pagoService.actualizarPago(id, pago);
            return new ResponseEntity<>(pagoActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar una pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        try {
            pagoService.eliminarPago(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content es lo ideal para deletes exitosos
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6. Buscar por fecha: /api/pagos/buscar-fecha?fecha=2026-06-19
    @GetMapping("/buscar-fecha")
    public ResponseEntity<List<PagoModel>> buscarPorFecha(@RequestParam("fecha") Date fecha) {
        List<PagoModel> pagos = pagoService.buscarPorFecha(fecha);
        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si no hay resultados
        }
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    // 7. Buscar por total: /api/pagos/buscar-total?total=1500
    @GetMapping("/buscar-total")
    public ResponseEntity<List<PagoModel>> buscarPorTotal(@RequestParam("total") Integer total) {
        List<PagoModel> pagos = pagoService.buscarPorTotal(total);
        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }
}