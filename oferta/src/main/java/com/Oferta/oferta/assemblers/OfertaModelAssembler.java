package com.Oferta.oferta.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Oferta.oferta.controller.OfertaControllerV2;
import com.Oferta.oferta.model.OfertaModel;

@Component
public class OfertaModelAssembler implements RepresentationModelAssembler<OfertaModel, EntityModel<OfertaModel>> {

    @Override
    public EntityModel<OfertaModel> toModel(OfertaModel oferta) {
        return EntityModel.of(oferta,
                // Link al recurso individual (Self)
                linkTo(methodOn(OfertaControllerV2.class).getOfertaById(oferta.getIdOferta())).withSelfRel(),

                // Link a la colección completa de recursos (Relación: "ofertas")
                linkTo(methodOn(OfertaControllerV2.class).getAllOfertas()).withRel("ofertas"));
    }
}
