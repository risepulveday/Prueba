package com.Pago.pago.controller;

import com.Pago.pago.assemblers.PagoModelAssembler;
import com.Pago.pago.model.PagoModel;
import com.Pago.pago.service.PagoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    private final PagoService pagoService;

    private final PagoModelAssembler assembler;

    PagoControllerV2(PagoService pagoService, PagoModelAssembler assembler) {
        this.pagoService = pagoService;
        this.assembler = assembler;
    }

    // 1. Obtener una pago por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<PagoModel> getPagoById(@PathVariable Long id) {
        PagoModel pago = pagoService.obtenerPorId(id) // Usando la variable inyectada 'pagoService'
                .orElseThrow(() -> new RuntimeException("Pago no encontrada con el id: " + id));

        return assembler.toModel(pago);
    }

    // 2. Obtener todas las pagos con HATEOAS (Requerido por tu Assembler)
    @GetMapping
    public CollectionModel<EntityModel<PagoModel>> getAllPagos() {
        List<EntityModel<PagoModel>> pagos = pagoService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel());
    }
}