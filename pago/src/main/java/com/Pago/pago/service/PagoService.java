package com.Pago.pago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa aquí tu cliente Feign y tu DTO de Pago cuando los tengas creados:
// import com.Pago.pago.client.PagoFeignClient;
// import com.Pago.pago.dto.PagoDTO;

import com.Pago.pago.model.PagoModel;
import com.Pago.pago.repository.PagoRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    // @Autowired
    // private PagoFeignClient pagoFeignClient; // Tu cliente Feign para conectar
    // con el microservicio de pagos

    // 1. Crear o Guardar una pago
    public PagoModel crearPago(PagoModel pago) {
        // Aquí podrías agregar lógica antes de guardar, por ejemplo, validar si el
        // idPago realmente existe usando Feign
        return pagoRepository.save(pago);
    }

    // 2. Obtener todas las pagos
    public List<PagoModel> obtenerTodas() {
        return pagoRepository.findAll();
    }

    // 3. Obtener una pago por ID
    public Optional<PagoModel> obtenerPorId(Long id) {
        return pagoRepository.findById(id);
    }

    // 4. Actualizar una pago
    public PagoModel actualizarPago(Long id, PagoModel pagoDetalles) {
        return pagoRepository.findById(id).map(pago -> {
            pago.setTotal(pagoDetalles.getTotal());
            pago.setFecha(pagoDetalles.getFecha());
            pago.setEstadoOrden(pagoDetalles.isEstadoOrden());
            pago.setIdPago(pagoDetalles.getIdPago());
            return pagoRepository.save(pago);
        }).orElseThrow(() -> new RuntimeException("Pago no encontrada con el id: " + id));
    }

    // 5. Eliminar una pago
    public void eliminarPago(Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pago no encontrada con el id: " + id);
        }
    }

    // 6. Buscar pagos por fecha
    public List<PagoModel> buscarPorFecha(Date fecha) {
        return pagoRepository.findByFecha(fecha);
    }

    // 7. Buscar pagos por total
    public List<PagoModel> buscarPorTotal(Integer total) {
        return pagoRepository.findByTotal(total);
    }

    // =========================================================================
    // EJEMPLO CON FEIGN: Obtener la pago junto con la información del Pago
    // =========================================================================
    /*
     * public PagoConPagoDTO obtenerPagoConSuPago(Long idPago) {
     * PagoModel pago = pagoRepository.findById(idPago)
     * .orElseThrow(() -> new RuntimeException("Pago no encontrada"));
     * 
     * // Consumimos el microservicio de pagos usando Feign mediante el id guardado
     * PagoDTO pago = pagoFeignClient.obtenerPagoPorId(pago.getIdPago());
     * 
     * // Armamos un DTO propio que junte ambas respuestas para el frontend
     * PagoConPagoDTO respuesta = new PagoConPagoDTO();
     * respuesta.setPago(pago);
     * respuesta.setPago(pago);
     * 
     * return respuesta;
     * }
     */
}