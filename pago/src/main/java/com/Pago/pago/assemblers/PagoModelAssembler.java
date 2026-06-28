package com.Pago.pago.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Pago.pago.controller.PagoControllerV2;
import com.Pago.pago.model.PagoModel;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoModel, EntityModel<PagoModel>> {

    @Override
    public EntityModel<PagoModel> toModel(PagoModel pago) {
        return EntityModel.of(pago,
                // Link al recurso individual (Self)
                linkTo(methodOn(PagoControllerV2.class).getPagoById(pago.getIdPago())).withSelfRel(),

                // Link a la colección completa de recursos (Relación: "pagos")
                linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withRel("pagos"));
    }
}
