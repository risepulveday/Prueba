package com.Dispositivo.dispositivo.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Dispositivo.dispositivo.controller.DispositivoControllerV2;
import com.Dispositivo.dispositivo.model.DispositivoModel;

@Component
public class DispositivoModelAssembler
        implements RepresentationModelAssembler<DispositivoModel, EntityModel<DispositivoModel>> {

    @Override
    public EntityModel<DispositivoModel> toModel(DispositivoModel dispositivo) {
        return EntityModel.of(dispositivo,
                linkTo(methodOn(DispositivoControllerV2.class).getDispositivoById(dispositivo.getIdDispositivo()))
                        .withSelfRel(),
                linkTo(methodOn(DispositivoControllerV2.class).getAllDispositivos()).withRel("dispositivos"),
                linkTo(methodOn(DispositivoControllerV2.class).getDispositivosByUsuario(dispositivo.getIdUsuario()))
                        .withRel("usuario-dispositivos"));
    }
}