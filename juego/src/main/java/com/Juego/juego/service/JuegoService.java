package com.Juego.juego.service;

import org.springframework.stereotype.Service;
import com.Juego.juego.model.JuegoModel;
import com.Juego.juego.repository.JuegoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {

    private final JuegoRepository juegoRepository;

    public JuegoService(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    public JuegoModel guardarJuego(JuegoModel juego) {
        return juegoRepository.save(juego);
    }

    public List<JuegoModel> obtenerTodos() {
        return juegoRepository.findAll();
    }

    public Optional<JuegoModel> obtenerPorId(Long id) {
        return juegoRepository.findById(id);
    }

    public JuegoModel actualizarJuego(Long id, JuegoModel detalles) {
        return juegoRepository.findById(id).map(juego -> {
            juego.setNombreJuego(detalles.getNombreJuego());
            juego.setPrecioJuego(detalles.getPrecioJuego());
            juego.setStock(detalles.getStock());
            return juegoRepository.save(juego);
        }).orElseThrow(() -> new RuntimeException("Juego no encontrado con el id: " + id));
    }

    public void eliminarJuego(Long id) {
        if (juegoRepository.existsById(id)) {
            juegoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Juego no encontrado con el id: " + id);
        }
    }
}