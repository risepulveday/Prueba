package com.Notificacion.notificacion.controller;

import com.Notificacion.notificacion.assemblers.NotificacionModelAssembler;
import com.Notificacion.notificacion.model.NotificacionModel;
import com.Notificacion.notificacion.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/notificacions")
public class NotificacionControllerV2{

    @Autowired
    private NotificacionService notificacionService; 

    @Autowired
    private NotificacionModelAssembler assembler;

    // 1. Obtener una notificacion por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<NotificacionModel> getNotificacionById(@PathVariable Long id) {
        NotificacionModel notificacion = notificacionService.obtenerPorId(id) // Usando la variable inyectada 'notificacionService'
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada con el id: " + id));
                
        return assembler.toModel(notificacion);
    }
    // 2. Obtener todas las notificacions con HATEOAS (Requerido por tu Assembler)
    @GetMapping
    public CollectionModel<EntityModel<NotificacionModel>> getAllNotificacions() {
        List<EntityModel<NotificacionModel>> notificacions = notificacionService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(notificacions,
                linkTo(methodOn(NotificacionControllerV2.class).getAllNotificacions()).withSelfRel());
    }
}