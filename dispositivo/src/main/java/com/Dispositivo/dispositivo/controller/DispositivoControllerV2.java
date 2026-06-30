package com.Dispositivo.dispositivo.controller;

import com.Dispositivo.dispositivo.assemblers.DispositivoModelAssembler;
import com.Dispositivo.dispositivo.model.DispositivoModel;
import com.Dispositivo.dispositivo.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/dispositivos")
public class DispositivoControllerV2{

    @Autowired
    private DispositivoService dispositivoService; 

    @Autowired
    private DispositivoModelAssembler assembler;

    // 1. Obtener una dispositivo por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<DispositivoModel> getDispositivoById(@PathVariable Long id) {
        DispositivoModel dispositivo = dispositivoService.obtenerPorId(id) // Usando la variable inyectada 'dispositivoService'
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrada con el id: " + id));
                
        return assembler.toModel(dispositivo);
    }
    // 2. Obtener todas las dispositivos con HATEOAS (Requerido por tu Assembler)
    @GetMapping
    public CollectionModel<EntityModel<DispositivoModel>> getAllDispositivos() {
        List<EntityModel<DispositivoModel>> dispositivos = dispositivoService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(dispositivos,
                linkTo(methodOn(DispositivoControllerV2.class).getAllDispositivos()).withSelfRel());
    }
}