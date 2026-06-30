package com.Pago.pago.service;

import org.springframework.stereotype.Service;
import com.Pago.pago.model.MetodoPagoModel;
import com.Pago.pago.repository.MetodoPagoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public MetodoPagoService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    // 1. Guardar un método de pago
    public MetodoPagoModel crearMetodoPago(MetodoPagoModel metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    // 2. Obtener todos los métodos de pago (Catálogo completo)
    public List<MetodoPagoModel> obtenerTodos() {
        return metodoPagoRepository.findAll();
    }

    // 3. Obtener un método específico por su ID
    public Optional<MetodoPagoModel> obtenerPorId(Long id) {
        return metodoPagoRepository.findById(id);
    }

    // 4. Actualizar el nombre de un método de pago
    public MetodoPagoModel actualizarMetodoPago(Long id, MetodoPagoModel detalles) {
        return metodoPagoRepository.findById(id).map(metodo -> {
            metodo.setNombreMetodoPago(detalles.getNombreMetodoPago());
            return metodoPagoRepository.save(metodo);
        }).orElseThrow(() -> new RuntimeException("Método de pago no encontrado con el id: " + id));
    }

    // 5. Eliminar un método de pago del catálogo
    public void eliminarMetodoPago(Long id) {
        if (metodoPagoRepository.existsById(id)) {
            metodoPagoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Método de pago no encontrado con el id: " + id);
        }
    }
}