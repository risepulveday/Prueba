package com.Pago.pago.service;

import org.springframework.stereotype.Service;
import com.Pago.pago.model.PagoModel;
import com.Pago.pago.repository.PagoRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    // 1. Crear o Guardar un pago
    public PagoModel crearPago(PagoModel pago) {
        return pagoRepository.save(pago);
    }

    // 2. Obtener todos los pagos
    public List<PagoModel> obtenerTodas() {
        return pagoRepository.findAll();
    }

    // 3. Obtener un pago por ID
    public Optional<PagoModel> obtenerPorId(Long id) {
        return pagoRepository.findById(id);
    }

    // 4. Actualizar un pago existente
    public PagoModel actualizarPago(Long id, PagoModel pagoDetalles) {
        return pagoRepository.findById(id).map(pago -> {
            pago.setFechaPago(pagoDetalles.getFechaPago());
            pago.setEstadoPago(pagoDetalles.isEstadoPago());
            pago.setMetodoPago(pagoDetalles.getMetodoPago());
            pago.setIdFactura(pagoDetalles.getIdFactura());
            return pagoRepository.save(pago);
        }).orElseThrow(() -> new RuntimeException("Pago no encontrada con el id: " + id));
    }

    // 5. Eliminar un pago
    public void eliminarPago(Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pago no encontrada con el id: " + id);
        }
    }

    // 6. Buscar pagos por fecha (Sincronizado con el cambio del Repositorio)
    public List<PagoModel> buscarPorFecha(Date fechaPago) {
        return pagoRepository.findByFechaPago(fechaPago);
    }
}