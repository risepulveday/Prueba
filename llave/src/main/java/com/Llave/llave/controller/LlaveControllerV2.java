package com.Llave.llave.controller;

import com.Llave.llave.assemblers.LlaveModelAssembler;
import com.Llave.llave.model.LlaveModel;
import com.Llave.llave.service.LlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/llaves")
public class LlaveControllerV2{

    @Autowired
    private LlaveService llaveService; 

    @Autowired
    private LlaveModelAssembler assembler;

    // 1. Obtener una llave por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<LlaveModel> getLlaveById(@PathVariable Long id) {
        LlaveModel llave = llaveService.obtenerPorId(id) // Usando la variable inyectada 'llaveService'
                .orElseThrow(() -> new RuntimeException("Llave no encontrada con el id: " + id));
                
        return assembler.toModel(llave);
    }
    // 2. Obtener todas las llaves con HATEOAS (Requerido por tu Assembler)
    @GetMapping
    public CollectionModel<EntityModel<LlaveModel>> getAllLlaves() {
        List<EntityModel<LlaveModel>> llaves = llaveService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(llaves,
                linkTo(methodOn(LlaveControllerV2.class).getAllLlaves()).withSelfRel());
    }
}