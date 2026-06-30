package com.Oferta.oferta.controller;

import com.Oferta.oferta.assemblers.OfertaModelAssembler;
import com.Oferta.oferta.model.OfertaModel;
import com.Oferta.oferta.service.OfertaService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/ofertas")
public class OfertaControllerV2 {

    private final OfertaService ofertaService;

    private final OfertaModelAssembler assembler;

    OfertaControllerV2(OfertaModelAssembler assembler, OfertaService ofertaService) {
        this.assembler = assembler;
        this.ofertaService = ofertaService;
    }

    // 1. Obtener una oferta por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<OfertaModel> getOfertaById(@PathVariable Long id) {
        OfertaModel oferta = ofertaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada con el id: " + id));

        return assembler.toModel(oferta);
    }

    // 2. Obtener todas las ofertas con HATEOAS
    @GetMapping
    public CollectionModel<EntityModel<OfertaModel>> getAllOfertas() {
        List<EntityModel<OfertaModel>> ofertas = ofertaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ofertas,
                linkTo(methodOn(OfertaControllerV2.class).getAllOfertas()).withSelfRel());
    }
}