package com.Dispositivo.dispositivo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import com.Dispositivo.dispositivo.assemblers.DispositivoModelAssembler;
import com.Dispositivo.dispositivo.model.DispositivoModel;
import com.Dispositivo.dispositivo.service.DispositivoService;

@RestController
@RequestMapping("/api/v2/dispositivos")
public class DispositivoControllerV2 {

    private final DispositivoService dispositivoService;

    private final DispositivoModelAssembler assembler;

    DispositivoControllerV2(DispositivoService dispositivoService, DispositivoModelAssembler assembler) {
        this.dispositivoService = dispositivoService;
        this.assembler = assembler;
    }

    // 1. Obtener un dispositivo por ID con enlaces HATEOAS
    @GetMapping("/{id}")
    public EntityModel<DispositivoModel> getDispositivoById(@PathVariable Long id) {
        DispositivoModel dispositivo = dispositivoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado con el id: " + id));

        return assembler.toModel(dispositivo);
    }

    // 2. Obtener la colección completa de dispositivos con enlaces de navegación
    @GetMapping
    public CollectionModel<EntityModel<DispositivoModel>> getAllDispositivos() {
        List<EntityModel<DispositivoModel>> dispositivos = dispositivoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(dispositivos,
                linkTo(methodOn(DispositivoControllerV2.class).getAllDispositivos()).withSelfRel());
    }

    // 3. Obtener los dispositivos de un usuario específico con HATEOAS
    @GetMapping("/usuario/{idUsuario}")
    public CollectionModel<EntityModel<DispositivoModel>> getDispositivosByUsuario(@PathVariable Long idUsuario) {
        List<EntityModel<DispositivoModel>> dispositivos = dispositivoService.obtenerPorUsuario(idUsuario).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(dispositivos,
                linkTo(methodOn(DispositivoControllerV2.class).getDispositivosByUsuario(idUsuario)).withSelfRel(),
                linkTo(methodOn(DispositivoControllerV2.class).getAllDispositivos()).withRel("todos-los-dispositivos"));
    }
}