package com.Notificacion.notificacion.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Notificacion.notificacion.controller.NotificacionControllerV2;
import com.Notificacion.notificacion.model.NotificacionModel;

@Component
public class NotificacionModelAssembler
        implements RepresentationModelAssembler<NotificacionModel, EntityModel<NotificacionModel>> {

    @Override
    public EntityModel<NotificacionModel> toModel(NotificacionModel notificacion) {
        return EntityModel.of(notificacion,
                // Link al recurso individual (Self)
                linkTo(methodOn(NotificacionControllerV2.class).getNotificacionById(notificacion.getIdNotificacion()))
                        .withSelfRel(),

                // Link a la colección completa de recursos (Relación: "notificaciones")
                linkTo(methodOn(NotificacionControllerV2.class).getAllNotificacions()).withRel("notificaciones"));
    }
}
