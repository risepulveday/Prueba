package com.Llave.llave.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Llave.llave.controller.LlaveControllerV2;
import com.Llave.llave.model.LlaveModel;

@Component
public class LlaveModelAssembler implements RepresentationModelAssembler<LlaveModel, EntityModel<LlaveModel>> {

    @Override
    public EntityModel<LlaveModel> toModel(LlaveModel llave) { // Cambiado de 'usuario' a 'factura'
        return EntityModel.of(llave,
                // Link al recurso individual (Self)
                linkTo(methodOn(LlaveControllerV2.class).getLlaveById(llave.getIdLlave())).withSelfRel(),

                // Link a la colección completa de recursos (Relación: "llaves")
                linkTo(methodOn(LlaveControllerV2.class).getAllLlaves()).withRel("llaves"));
    }
}
