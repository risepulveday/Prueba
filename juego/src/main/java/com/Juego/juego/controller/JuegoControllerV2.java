package com.Juego.juego.controller;

import com.Juego.juego.assemblers.JuegoModelAssembler;
import com.Juego.juego.model.JuegoModel;
import com.Juego.juego.service.JuegoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/juegos")
public class JuegoControllerV2 {

    private final JuegoService juegoService;
    private final JuegoModelAssembler assembler;

    JuegoControllerV2(JuegoService juegoService, JuegoModelAssembler assembler) {
        this.juegoService = juegoService;
        this.assembler = assembler;
    }

    // 1. Obtener un Juego por ID con soporte HATEOAS
    @GetMapping("/{id}")
    public EntityModel<JuegoModel> getJuegoById(@PathVariable Long id) {
        JuegoModel juego = juegoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado con el id: " + id));

        return assembler.toModel(juego);
    }

    // 2. Obtener todos los Juegos con enlaces hipermedia de HATEOAS
    @GetMapping
    public CollectionModel<EntityModel<JuegoModel>> getAllJuegos() {
        List<EntityModel<JuegoModel>> juegos = juegoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(juegos);
    }
}