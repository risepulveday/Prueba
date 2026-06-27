package com.Pago.pago.controller;

import com.Pago.pago.assemblers.PagoModelAssembler;
import com.Pago.pago.model.PagoModel;
import com.Pago.pago.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    @Autowired
    private PagoService PagoService;

    @Autowired
    private PagoModelAssembler assembler;

    // 1. Obtener una factura por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<PagoModel> getFacturaById(@PathVariable Long id) {
        PagoModel factura = facturaService.obtenerPorId(id) // Usando la variable inyectada 'facturaService'
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con el id: " + id));

        return assembler.toModel(factura);
    }

    // 2. Obtener todas las facturas con HATEOAS (Requerido por tu Assembler)
    @GetMapping
    public CollectionModel<EntityModel<PagoModel>> getAllFacturas() {
        List<EntityModel<PagoModel>> facturas = facturaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(facturas,
                linkTo(methodOn(PagoControllerV2.class).getAllFacturas()).withSelfRel());
    }
}