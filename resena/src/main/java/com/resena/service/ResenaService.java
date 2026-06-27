package com.Resena.resena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa aquí tu cliente Feign y tu DTO de Pago cuando los tengas creados:
// import com.Resena.resena.client.PagoFeignClient;
// import com.Resena.resena.dto.PagoDTO;

import com.Resena.resena.model.ResenaModel;
import com.Resena.resena.repository.ResenaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;
    // @Autowired
    // private PagoFeignClient pagoFeignClient; // Tu cliente Feign para conectar
    // con el microservicio de pagos

    // 1. Crear o Guardar una resena
    public ResenaModel crearResena(ResenaModel resena) {
        // Aquí podrías agregar lógica antes de guardar, por ejemplo, validar si el
        // idPago realmente existe usando Feign
        return resenaRepository.save(resena);
    }

    // 2. Obtener todas las resenas
    public List<ResenaModel> obtenerTodas() {
        return resenaRepository.findAll();
    }

    // 3. Obtener una resena por ID
    public Optional<ResenaModel> obtenerPorId(Long id) {
        return resenaRepository.findById(id);
    }

    // 4. Actualizar una resena
    public ResenaModel actualizarResena(Long id, ResenaModel resenaDetalles) {
        return resenaRepository.findById(id).map(resena -> {
            resena.setTotal(resenaDetalles.getTotal());
            resena.setFecha(resenaDetalles.getFecha());
            resena.setEstadoOrden(resenaDetalles.isEstadoOrden());
            resena.setIdPago(resenaDetalles.getIdPago());
            return resenaRepository.save(resena);
        }).orElseThrow(() -> new RuntimeException("Resena no encontrada con el id: " + id));
    }

    // 5. Eliminar una resena
    public void eliminarResena(Long id) {
        if (resenaRepository.existsById(id)) {
            resenaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Resena no encontrada con el id: " + id);
        }
    }

    // 6. Buscar resenas por fecha
    public List<ResenaModel> buscarPorFecha(Date fecha) {
        return resenaRepository.findByFecha(fecha);
    }

    // 7. Buscar resenas por total
    public List<ResenaModel> buscarPorTotal(Integer total) {
        return resenaRepository.findByTotal(total);
    }

    // =========================================================================
    // EJEMPLO CON FEIGN: Obtener la resena junto con la información del Pago
    // =========================================================================
    /*
     * public ResenaConPagoDTO obtenerResenaConSuPago(Long idResena) {
     * ResenaModel resena = resenaRepository.findById(idResena)
     * .orElseThrow(() -> new RuntimeException("Resena no encontrada"));
     * 
     * // Consumimos el microservicio de pagos usando Feign mediante el id guardado
     * PagoDTO pago = pagoFeignClient.obtenerPagoPorId(resena.getIdPago());
     * 
     * // Armamos un DTO propio que junte ambas respuestas para el frontend
     * ResenaConPagoDTO respuesta = new ResenaConPagoDTO();
     * respuesta.setResena(resena);
     * respuesta.setPago(pago);
     * 
     * return respuesta;
     * }
     */
}