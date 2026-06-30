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

    // 1. Crear un nuevo pago
    @PostMapping
    public ResponseEntity<PagoModel> crearPago(@Valid @RequestBody PagoModel pago) {
        PagoModel nuevaPago = pagoService.crearPago(pago);
        return new ResponseEntity<>(nuevaPago, HttpStatus.CREATED);
    }

    // 2. Obtener todos los pagos registrados
    @GetMapping
    public ResponseEntity<List<PagoModel>> obtenerTodas() {
        List<PagoModel> pagos = pagoService.obtenerTodas();
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }

    // 3. Obtener un pago por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagoModel> obtenerPorId(@PathVariable Long id) {
        return pagoService.obtenerPorId(id)
                .map(pago -> new ResponseEntity<>(pago, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar un pago existente
    @PutMapping("/{id}")
    public ResponseEntity<PagoModel> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoModel pago) {
        try {
            PagoModel pagoActualizada = pagoService.actualizarPago(id, pago);
            return new ResponseEntity<>(pagoActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar un pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        try {
            pagoService.eliminarPago(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6. Buscar por fecha
    @GetMapping("/buscar-fecha")
    public ResponseEntity<List<PagoModel>> buscarPorFecha(@RequestParam("fecha") Date fecha) {
        List<PagoModel> pagos = pagoService.buscarPorFecha(fecha);
        if (pagos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }
}