package com.Resena.resena.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Resena.resena.controller.ResenaControllerV2;
import com.Resena.resena.model.ResenaModel;

@Component
public class ResenaModelAssembler implements RepresentationModelAssembler<ResenaModel, EntityModel<ResenaModel>> {

    @Override
    public EntityModel<ResenaModel> toModel(ResenaModel resena) { // Cambiado de 'usuario' a 'resena'
        return EntityModel.of(resena,
                // Link al recurso individual (Self)
                linkTo(methodOn(ResenaControllerV2.class).getResenaById(resena.getIdResena())).withSelfRel(),

                // Link a la colección completa de recursos (Relación: "resenas")
                linkTo(methodOn(ResenaControllerV2.class).getAllResenas()).withRel("resenas"));
    }
}
