package com.Oferta.oferta.controller;

import com.Oferta.oferta.assemblers.OfertaModelAssembler;
import com.Oferta.oferta.model.OfertaModel;
import com.Oferta.oferta.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/pagos")
public class OfertaControllerV2{

    @Autowired
    private OfertaService pagoService; 

    @Autowired
    private OfertaModelAssembler assembler;

    // 1. Obtener una pago por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<OfertaModel> getOfertaById(@PathVariable Long id) {
        OfertaModel pago = pagoService.obtenerPorId(id) // Usando la variable inyectada 'pagoService'
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada con el id: " + id));
                
        return assembler.toModel(pago);
    }
    // 2. Obtener todas los pagos con HATEOAS (Requerido por tu Assembler)
    @GetMapping
    public CollectionModel<EntityModel<OfertaModel>> getAllOfertas() {
        List<EntityModel<OfertaModel>> pagos = pagoService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pagos,
                linkTo(methodOn(OfertaControllerV2.class).getAllOfertas()).withSelfRel());
    }
}