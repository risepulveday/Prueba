package com.Resena.resena.controller;

import com.Resena.resena.assemblers.ResenaModelAssembler;
import com.Resena.resena.model.ResenaModel;
import com.Resena.resena.service.ResenaService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/resenas")
public class ResenaControllerV2 {

    private final ResenaService resenaService;

    private final ResenaModelAssembler assembler;

    ResenaControllerV2(ResenaService resenaService, ResenaModelAssembler assembler) {
        this.resenaService = resenaService;
        this.assembler = assembler;
    }

    // 1. Obtener una resena por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<ResenaModel> getResenaById(@PathVariable Long id) {
        ResenaModel resena = resenaService.obtenerPorId(id) // Usando la variable inyectada 'resenaService'
                .orElseThrow(() -> new RuntimeException("Resena no encontrada con el id: " + id));

        return assembler.toModel(resena);
    }

    // 2. Obtener todas las resenas con HATEOAS (Requerido por tu Assembler)
    @GetMapping
    public CollectionModel<EntityModel<ResenaModel>> getAllResenas() {
        List<EntityModel<ResenaModel>> resenas = resenaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(resenas,
                linkTo(methodOn(ResenaControllerV2.class).getAllResenas()).withSelfRel());
    }
}