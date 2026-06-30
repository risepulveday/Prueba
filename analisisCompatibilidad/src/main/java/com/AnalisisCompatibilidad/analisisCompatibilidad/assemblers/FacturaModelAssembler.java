package com.Factura.factura.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Factura.factura.controller.FacturaControllerV2;
import com.Factura.factura.model.FacturaModel;

@Component
public class FacturaModelAssembler implements RepresentationModelAssembler<FacturaModel, EntityModel<FacturaModel>> {

    @Override
    public EntityModel<FacturaModel> toModel(FacturaModel factura) { // Cambiado de 'usuario' a 'factura'
        return EntityModel.of(factura,
            // Link al recurso individual (Self)
            linkTo(methodOn(FacturaControllerV2.class).getFacturaById(factura.getIdFactura())).withSelfRel(),
            
            // Link a la colección completa de recursos (Relación: "facturas")
            linkTo(methodOn(FacturaControllerV2.class).getAllFacturas()).withRel("facturas")
        );
    }
}

