package com.Llave.llave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa aquí tu cliente Feign y tu DTO de Pago cuando los tengas creados:
// import com.Llave.llave.client.PagoFeignClient;
// import com.Llave.llave.dto.PagoDTO;

import com.Llave.llave.model.LlaveModel;
import com.Llave.llave.repository.LlaveRepository;
import java.util.List;
import java.util.Optional;

@Service
public class LlaveService {

    @Autowired
    private LlaveRepository llaveRepository;
    // @Autowired
    // private PagoFeignClient pagoFeignClient; // Tu cliente Feign para conectar
    // con el microservicio de pagos

    // 1. Crear o Guardar una llave
    public LlaveModel crearLlave(LlaveModel llave) {
        // Aquí podrías agregar lógica antes de guardar, por ejemplo, validar si el
        // idPago realmente existe usando Feign
        return llaveRepository.save(llave);
    }

    // 2. Obtener todas las llaves
    public List<LlaveModel> obtenerTodas() {
        return llaveRepository.findAll();
    }

    // 3. Obtener una llave por ID
    public Optional<LlaveModel> obtenerPorId(Long id) {
        return llaveRepository.findById(id);
    }

    // 4. Actualizar una llave
    public LlaveModel actualizarLlave(Long id, LlaveModel llaveDetalles) {
        return llaveRepository.findById(id).map(llave -> {
            llave.setIdJuego(llaveDetalles.getIdJuego());
            return llaveRepository.save(llave);
        }).orElseThrow(() -> new RuntimeException("Llave no encontrada con el id: " + id));
    }

    // 5. Eliminar una llave
    public void eliminarLlave(Long id) {
        if (llaveRepository.existsById(id)) {
            llaveRepository.deleteById(id);
        } else {
            throw new RuntimeException("Llave no encontrada con el id: " + id);
        }
    }

    // 6. Buscar llaves por juego
    public List<LlaveModel> buscarPorJuego(Long idJuego) {
        return llaveRepository.findByIdJuego(idJuego);
    }

    // =========================================================================
    // EJEMPLO CON FEIGN: Obtener la llave junto con la información del Pago
    // =========================================================================
    /*
     * public LlaveConPagoDTO obtenerLlaveConSuPago(Long idLlave) {
     * LlaveModel llave = llaveRepository.findById(idLlave)
     * .orElseThrow(() -> new RuntimeException("Llave no encontrada"));
     * 
     * // Consumimos el microservicio de pagos usando Feign mediante el id guardado
     * PagoDTO pago = pagoFeignClient.obtenerPagoPorId(llave.getIdPago());
     * 
     * // Armamos un DTO propio que junte ambas respuestas para el frontend
     * LlaveConPagoDTO respuesta = new LlaveConPagoDTO();
     * respuesta.setLlave(llave);
     * respuesta.setPago(pago);
     * 
     * return respuesta;
     * }
     */
}