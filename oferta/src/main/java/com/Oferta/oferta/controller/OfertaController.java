package com.Oferta.oferta.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Oferta.pago.model.OfertaModel;
import com.Oferta.pago.service.OfertaService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/oferta")
public class OfertaController {
    @Autowired
    private OfertaService ofertaService;

    // 1. Crear una nueva oferta
    @PostMapping
    public ResponseEntity<OfertaModel> crearOferta(@Valid @RequestBody OfertaModel oferta) {
        OfertaModel nuevaOferta = ofertaService.crearOferta(oferta);
        return new ResponseEntity<>(nuevaOferta, HttpStatus.CREATED);
    }

    // 2. Obtener todas las ofertas
    @GetMapping
    public ResponseEntity<List<OfertaModel>> obtenerTodas() {
        List<OfertaModel> ofertas = ofertaService.obtenerTodas();
        return new ResponseEntity<>(ofertas, HttpStatus.OK);
    }

    // 3. Obtener una oferta por ID
    @GetMapping("/{id}")
    public ResponseEntity<OfertaModel> obtenerPorId(@PathVariable Long id) {
        return ofertaService.obtenerPorId(id)
                .map(oferta -> new ResponseEntity<>(oferta, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualizar una oferta existente
    @PutMapping("/{id}")
    public ResponseEntity<OfertaModel> actualizarOferta(@PathVariable Long id, @Valid @RequestBody OfertaModel oferta) {
        try {
            OfertaModel ofertaActualizada = ofertaService.actualizarOferta(id, oferta);
            return new ResponseEntity<>(ofertaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Eliminar una oferta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Long id) {
        try {
            ofertaService.eliminarOferta(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content es lo ideal para deletes exitosos
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //6. Buscar por descuento: /api/ofertas/buscar-descuento?descuento=20
    @GetMapping("/buscar-descuento")
    public ResponseEntity<List<OfertaModel>> buscarPorDescuento(@RequestParam("descuento") Integer descuento) {
        List<OfertaModel> ofertas = ofertaService.buscarPorDescuento(descuento);
        if (ofertas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ofertas, HttpStatus.OK);
    }
}