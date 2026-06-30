package com.Oferta.oferta.service;

import org.springframework.stereotype.Service;
import com.Oferta.oferta.client.JuegoFeignClient;
import com.Oferta.oferta.dto.JuegoDTO;
import com.Oferta.oferta.model.OfertaModel;
import com.Oferta.oferta.repository.OfertaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    private final JuegoFeignClient juegoFeignClient;

    OfertaService(OfertaRepository ofertaRepository, JuegoFeignClient juegoFeignClient) {
        this.ofertaRepository = ofertaRepository;
        this.juegoFeignClient = juegoFeignClient;
    }

    // 1. Crear o Guardar una oferta con validación inter-servicio
    public OfertaModel crearOferta(OfertaModel oferta) {
        try {
            // buscar el juego en el puerto 8084
            JuegoDTO juego = juegoFeignClient.obtenerJuegoPorId(oferta.getIdJuego());
            if (juego == null) {
                throw new RuntimeException("El juego no existe en el catálogo.");
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error al conectar con el microservicio de Juegos o el juego no existe. " + e.getMessage());
        }

        return ofertaRepository.save(oferta);
    }

    // 4. Actualizar una oferta existente
    public OfertaModel actualizarOferta(Long id, OfertaModel ofertaDetalles) {
        return ofertaRepository.findById(id).map(oferta -> {
            // Validar también en la actualización si cambian el idJuego
            try {
                juegoFeignClient.obtenerJuegoPorId(ofertaDetalles.getIdJuego());
            } catch (Exception e) {
                throw new RuntimeException("No se puede actualizar: el nuevo idJuego no existe.");
            }

            oferta.setDescuento(ofertaDetalles.getDescuento());
            oferta.setIdJuego(ofertaDetalles.getIdJuego());
            oferta.setFechaInicio(ofertaDetalles.getFechaInicio());
            oferta.setFechaFin(ofertaDetalles.getFechaFin());
            return ofertaRepository.save(oferta);
        }).orElseThrow(() -> new RuntimeException("Oferta no encontrada con el id: " + id));
    }

    public List<OfertaModel> obtenerTodas() {
        return ofertaRepository.findAll();
    }

    public Optional<OfertaModel> obtenerPorId(Long id) {
        return ofertaRepository.findById(id);
    }

    public void eliminarOferta(Long id) {
        ofertaRepository.deleteById(id);
    }

    public List<OfertaModel> buscarPorDescuento(Integer d) {
        return ofertaRepository.findByDescuento(d);
    }
}