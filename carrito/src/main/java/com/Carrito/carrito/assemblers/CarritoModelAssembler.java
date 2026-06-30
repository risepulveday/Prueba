package com.Carrito.carrito.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.Carrito.carrito.controller.CarritoControllerV2;
import com.Carrito.carrito.model.CarritoModel;

@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<CarritoModel, EntityModel<CarritoModel>> {

    @Override
    public EntityModel<CarritoModel> toModel(CarritoModel Carrito) { // Cambiado de 'usuario' a 'Carrito'
        return EntityModel.of(Carrito,
                // Link al recurso individual (Self)
                linkTo(methodOn(CarritoControllerV2.class).getCarritoById(Carrito.getIdCarrito())).withSelfRel(),

                // Link a la colección completa de recursos (Relación: "Carritos")
                linkTo(methodOn(CarritoControllerV2.class).getAllCarritos()).withRel("Carritos"));
    }
}
