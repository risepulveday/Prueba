package com.Carrito.carrito.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import com.Carrito.carrito.assemblers.CarritoModelAssembler;
import com.Carrito.carrito.model.CarritoModel;
import com.Carrito.carrito.service.CarritoService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/Carritos")
public class CarritoControllerV2 {

    private final CarritoService carritoService;
    private final CarritoModelAssembler assembler;

    // Inyección limpia por constructor (Evita advertencias de @Autowired)
    public CarritoControllerV2(CarritoService carritoService, CarritoModelAssembler assembler) {
        this.carritoService = carritoService;
        this.assembler = assembler;
    }

    // 1. Obtener un Carrito por ID con soporte HATEOAS
    @GetMapping("/{id}")
    public EntityModel<CarritoModel> getCarritoById(@PathVariable Long id) {
        CarritoModel carrito = carritoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrada con el id: " + id));

        return assembler.toModel(carrito);
    }

    // 2. Obtener todos los Carritos con enlaces hipermedia de HATEOAS
    @GetMapping
    public CollectionModel<EntityModel<CarritoModel>> getAllCarritos() {
        List<EntityModel<CarritoModel>> carritos = carritoService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(carritos,
                linkTo(methodOn(CarritoControllerV2.class).getAllCarritos()).withSelfRel());
    }
}