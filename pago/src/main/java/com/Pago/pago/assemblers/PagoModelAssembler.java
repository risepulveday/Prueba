package com.Pago.pago.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Factura.factura.controller.PagoControllerV2;
import com.Factura.factura.model.PagoModel;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoModel, EntityModel<PagoModel>> {

    @Override
    public EntityModel<PagoModel> toModel(PagoModel factura) { // Cambiado de 'usuario' a 'factura'
        return EntityModel.of(factura,
                // Link al recurso individual (Self)
                linkTo(methodOn(PagoControllerV2.class).getFacturaById(factura.getIdFactura())).withSelfRel(),

                // Link a la colección completa de recursos (Relación: "facturas")
                linkTo(methodOn(PagoControllerV2.class).getAllFacturas()).withRel("facturas"));
    }
}
