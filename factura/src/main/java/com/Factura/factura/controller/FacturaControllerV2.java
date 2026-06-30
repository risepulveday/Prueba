package com.Factura.factura.controller;

import com.Factura.factura.assemblers.FacturaModelAssembler;
import com.Factura.factura.model.FacturaModel;
import com.Factura.factura.service.FacturaService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/facturas")
public class FacturaControllerV2 {

    private final FacturaService facturaService;

    private final FacturaModelAssembler assembler;

    FacturaControllerV2(FacturaService facturaService, FacturaModelAssembler assembler) {
        this.facturaService = facturaService;
        this.assembler = assembler;
    }

    // 1. Obtener una factura por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<FacturaModel> getFacturaById(@PathVariable Long id) {
        FacturaModel factura = facturaService.obtenerPorId(id) // Usando la variable inyectada 'facturaService'
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con el id: " + id));

        return assembler.toModel(factura);
    }

    // 2. Obtener todas las facturas con HATEOAS (Requerido por tu Assembler)
    @GetMapping
    public CollectionModel<EntityModel<FacturaModel>> getAllFacturas() {
        List<EntityModel<FacturaModel>> facturas = facturaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(facturas,
                linkTo(methodOn(FacturaControllerV2.class).getAllFacturas()).withSelfRel());
    }
}