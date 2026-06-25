package com.Factura.factura.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Factura.factura.model.FacturaModel;
import com.Factura.factura.service.FacturaService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/factura")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    // 1. Crear una nueva factura
    @PostMapping
    public ResponseEntity<FacturaModel> crearFactura(@Valid @RequestBody FacturaModel factura) {
        FacturaModel nuevaFactura = facturaService.crearFactura(factura);
        return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
    }

    // 2. Obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<FacturaModel>> obtenerTodas() {
        List<FacturaModel> facturas = facturaService.obtenerTodas();
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    // 3. Obtener una factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<FacturaModel> obtenerPorId(@PathVariable Long id) {
        return facturaService.obtenerPorId(id)
                .map(factura -> new ResponseEntity<>(factura, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar una factura existente
    @PutMapping("/{id}")
    public ResponseEntity<FacturaModel> actualizarFactura(@PathVariable Long id, @Valid @RequestBody FacturaModel factura) {
        try {
            FacturaModel facturaActualizada = facturaService.actualizarFactura(id, factura);
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

    //6. Buscar por fecha: /api/facturas/buscar-fecha?fecha=2026-06-19
    @GetMapping("/buscar-fecha")
    public ResponseEntity<List<FacturaModel>> buscarPorFecha(@RequestParam("fecha") Date fecha) {
        List<FacturaModel> facturas = facturaService.buscarPorFecha(fecha);
        if (facturas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 si no hay resultados
        }
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    //7. Buscar por total: /api/facturas/buscar-total?total=1500
    @GetMapping("/buscar-total")
    public ResponseEntity<List<FacturaModel>> buscarPorTotal(@RequestParam("total") Integer total) {
        List<FacturaModel> facturas = facturaService.buscarPorTotal(total);
        if (facturas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }
}