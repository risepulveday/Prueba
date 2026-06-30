package com.Factura.factura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa aquí tu cliente Feign y tu DTO de Pago cuando los tengas creados:
// import com.Factura.factura.client.PagoFeignClient;
// import com.Factura.factura.dto.PagoDTO;

import com.Factura.factura.model.FacturaModel;
import com.Factura.factura.repository.FacturaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;
    // @Autowired
    // private PagoFeignClient pagoFeignClient; // Tu cliente Feign para conectar con el microservicio de pagos

    // 1. Crear o Guardar una factura
    public FacturaModel crearFactura(FacturaModel factura) {
        // Aquí podrías agregar lógica antes de guardar, por ejemplo, validar si el idPago realmente existe usando Feign
        return facturaRepository.save(factura);
    }

    // 2. Obtener todas las facturas
    public List<FacturaModel> obtenerTodas() {
        return facturaRepository.findAll();
    }

    // 3. Obtener una factura por ID
    public Optional<FacturaModel> obtenerPorId(Long id) {
        return facturaRepository.findById(id);
    }

    // 4. Actualizar una factura
    public FacturaModel actualizarFactura(Long id, FacturaModel facturaDetalles) {
        return facturaRepository.findById(id).map(factura -> {
            factura.setTotal(facturaDetalles.getTotal());
            factura.setFecha(facturaDetalles.getFecha());
            factura.setEstadoOrden(facturaDetalles.isEstadoOrden());
            factura.setIdPago(facturaDetalles.getIdPago());
            return facturaRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada con el id: " + id));
    }

    // 5. Eliminar una factura
    public void eliminarFactura(Long id) {
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Factura no encontrada con el id: " + id);
        }
    }

    // 6. Buscar facturas por fecha
    public List<FacturaModel> buscarPorFecha(Date fecha) {
        return facturaRepository.findByFecha(fecha);
    }

    // 7. Buscar facturas por total
    public List<FacturaModel> buscarPorTotal(Integer total) {
        return facturaRepository.findByTotal(total);
    }

    // =========================================================================
    // EJEMPLO CON FEIGN: Obtener la factura junto con la información del Pago
    // =========================================================================
    /* public FacturaConPagoDTO obtenerFacturaConSuPago(Long idFactura) {
        FacturaModel factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        // Consumimos el microservicio de pagos usando Feign mediante el id guardado
        PagoDTO pago = pagoFeignClient.obtenerPagoPorId(factura.getIdPago());

        // Armamos un DTO propio que junte ambas respuestas para el frontend
        FacturaConPagoDTO respuesta = new FacturaConPagoDTO();
        respuesta.setFactura(factura);
        respuesta.setPago(pago);

        return respuesta;
    }
    */
}