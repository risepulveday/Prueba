package com.Oferta.oferta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa aquí tu cliente Feign y tu DTO de Pago cuando los tengas creados:
// import com.Oferta.oferta.client.PagoFeignClient;
// import com.Oferta.oferta.dto.PagoDTO;

import com.Oferta.oferta.model.OfertaModel;
import com.Oferta.oferta.repository.OfertaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;
    // @Autowired
    // private PagoFeignClient pagoFeignClient; // Tu cliente Feign para conectar con el microservicio de pagos

    // 1. Crear o Guardar una oferta
    public OfertaModel crearOferta(OfertaModel oferta) {
        // Aquí podrías agregar lógica antes de guardar, por ejemplo, validar si el idPago realmente existe usando Feign
        return ofertaRepository.save(oferta);
    }

    // 2. Obtener todas las ofertas
    public List<OfertaModel> obtenerTodas() {
        return ofertaRepository.findAll();
    }

    // 3. Obtener una oferta por ID
    public Optional<OfertaModel> obtenerPorId(Long id) {
        return ofertaRepository.findById(id);
    }

    // 4. Actualizar una oferta
    public OfertaModel actualizarOferta(Long id, OfertaModel ofertaDetalles) {
        return ofertaRepository.findById(id).map(oferta -> {
            oferta.setDescuento(ofertaDetalles.getDescuento());
            oferta.setIdJuego(ofertaDetalles.getIdJuego());
            return ofertaRepository.save(oferta);
        }).orElseThrow(() -> new RuntimeException("Oferta no encontrada con el id: " + id));
    }

    // 5. Eliminar una oferta
    public void eliminarOferta(Long id) {
        if (ofertaRepository.existsById(id)) {
            ofertaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Oferta no encontrada con el id: " + id);
        }
    }

    // 7. Buscar ofertas por descuento
    public List<OfertaModel> buscarPorDescuento(Integer descuento) {
        return ofertaRepository.findByDescuento(descuento);
    }
}