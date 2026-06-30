package com.Juego.juego.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Juego.juego.controller.JuegoControllerV2;
import com.Juego.juego.model.JuegoModel;

@Component
public class JuegoModelAssembler implements RepresentationModelAssembler<JuegoModel, EntityModel<JuegoModel>> {

    @Override
    public EntityModel<JuegoModel> toModel(JuegoModel juego) {
        return EntityModel.of(juego,
                // Link al recurso individual (Self)
                linkTo(methodOn(JuegoControllerV2.class).getJuegoById(juego.getIdJuego())).withSelfRel(),

                // Link a la colección completa (Relación: "juegos")
                linkTo(methodOn(JuegoControllerV2.class).getAllJuegos()).withRel("juegos"));
    }
}