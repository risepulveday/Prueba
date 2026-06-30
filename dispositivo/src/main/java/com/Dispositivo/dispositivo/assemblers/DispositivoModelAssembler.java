package com.Dispositivo.dispositivo.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Dispositivo.dispositivo.controller.DispositivoControllerV2;
import com.Dispositivo.dispositivo.model.DispositivoModel;

@Component
public class DispositivoModelAssembler implements RepresentationModelAssembler<DispositivoModel, EntityModel<DispositivoModel>> {

    @Override
    public EntityModel<DispositivoModel> toModel(DispositivoModel dispositivo) { // Cambiado de 'usuario' a 'dispositivo'
        return EntityModel.of(dispositivo,
            // Link al recurso individual (Self)
            linkTo(methodOn(DispositivoControllerV2.class).getDispositivoById(dispositivo.getIdDispositivo())).withSelfRel(),
            
            // Link a la colección completa de recursos (Relación: "dispositivos")
            linkTo(methodOn(DispositivoControllerV2.class).getAllDispositivos()).withRel("dispositivos")
        );
    }
}

