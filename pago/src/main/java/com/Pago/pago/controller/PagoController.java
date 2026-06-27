package com.Pago.pago.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PagoService facturaService;

    // 1. Crear una nueva factura
    @PostMapping
    public ResponseEntity<PagoModel> crearFactura(@Valid @RequestBody PagoModel factura) {
        PagoModel nuevaFactura = facturaService.crearFactura(factura);
        return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
    }

    // 2. Obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<PagoModel>> obtenerTodas() {
        List<PagoModel> facturas = facturaService.obtenerTodas();
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    // 3. Obtener una factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagoModel> obtenerPorId(@PathVariable Long id) {
        return facturaService.obtenerPorId(id)
                .map(factura -> new ResponseEntity<>(factura, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar una factura existente
    @PutMapping("/{id}")
    public ResponseEntity<PagoModel> actualizarFactura(@PathVariable Long id, @Valid @RequestBody PagoModel factura) {
        try {
            PagoModel facturaActualizada = facturaService.actualizarFactura(id, factura);
            return new ResponseEntity<>(facturaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar una factura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        try {
            facturaService.eliminarFactura(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content es lo ideal para deletes exitosos
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6. Buscar por fecha: /api/facturas/buscar-fecha?fecha=2026-06-19
    @GetMapping("/buscar-fecha")
    public ResponseEntity<List<PagoModel>> buscarPorFecha(@RequestParam("fecha") Date fecha) {
        List<PagoModel> facturas = facturaService.buscarPorFecha(fecha);
        if (facturas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si no hay resultados
        }
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    // 7. Buscar por total: /api/facturas/buscar-total?total=1500
    @GetMapping("/buscar-total")
    public ResponseEntity<List<PagoModel>> buscarPorTotal(@RequestParam("total") Integer total) {
        List<PagoModel> facturas = facturaService.buscarPorTotal(total);
        if (facturas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }
}