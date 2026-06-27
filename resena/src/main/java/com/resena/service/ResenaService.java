package com.resena.service;

import org.springframework.stereotype.Service;

// Importa aquí tu cliente Feign y tu DTO de Usuario cuando los tengas creados:
// import com.resena.client.UsuarioFeignClient;
// import com.resena.dto.UsuarioDTO;

import com.resena.model.ResenaModel;
import com.resena.repository.ResenaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;

    // // Tu cliente Feign para conectar con el microservicio de usuarios si fuera
    // necesario
    // private final UsuarioFeignClient usuarioFeignClient;

    // Inyección por Constructor (Evita el warning de VS Code)
    public ResenaService(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    // 1. Crear o Guardar una reseña
    public ResenaModel crearResena(ResenaModel resena) {
        // Lógica de negocio opcional: Validar si el idUsuario existe en el
        // microservicio de usuarios vía Feign antes de guardar.
        return resenaRepository.save(resena);
    }

    // 2. Obtener todas las reseñas
    public List<ResenaModel> obtenerTodas() {
        return resenaRepository.findAll();
    }

    // 3. Obtener una reseña por ID
    public Optional<ResenaModel> obtenerPorId(Long id) {
        return resenaRepository.findById(id);
    }

    // 4. Actualizar una reseña (Adaptado a tu ResenaModel real)
    public ResenaModel actualizarResena(Long id, ResenaModel resenaDetalles) {
        return resenaRepository.findById(id).map(resena -> {
            resena.setCalificacion(resenaDetalles.getCalificacion());
            resena.setFecha(resenaDetalles.getFecha());
            resena.setComentario(resenaDetalles.getComentario());
            resena.setIdUsuario(resenaDetalles.getIdUsuario());
            return resenaRepository.save(resena);
        }).orElseThrow(() -> new RuntimeException("Reseña no encontrada con el id: " + id));
    }

    // 5. Eliminar una reseña
    public void eliminarResena(Long id) {
        if (resenaRepository.existsById(id)) {
            resenaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reseña no encontrada con el id: " + id);
        }
    }

    // 6. Buscar reseñas por fecha
    public List<ResenaModel> buscarPorFecha(Date fecha) {
        return resenaRepository.findByFecha(fecha);
    }

    // 7. Buscar reseñas por calificación (Corregido de 'buscarPorTotal' a
    // 'buscarPorCalificacion')
    public List<ResenaModel> buscarPorCalificacion(Integer calificacion) {
        return resenaRepository.findByCalificacion(calificacion);
    }

    // 8. Buscar reseñas asociadas a un usuario específico (Muy útil para este
    // microservicio)
    public List<ResenaModel> buscarPorUsuario(Long idUsuario) {
        return resenaRepository.findByIdUsuario(idUsuario);
    }

    // =========================================================================
    // EJEMPLO CON FEIGN: Obtener la reseña junto con la información del Usuario
    // =========================================================================
    /*
     * public ResenaConUsuarioDTO obtenerResenaConSuUsuario(Long idResena) {
     * ResenaModel resena = resenaRepository.findById(idResena)
     * .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
     * * // Consumimos el microservicio de usuarios usando Feign mediante el
     * id_usuario guardado
     * UsuarioDTO usuario =
     * usuarioFeignClient.obtenerUsuarioPorId(resena.getIdUsuario());
     * * // Armamos un DTO propio que junte ambas respuestas para devolverlo
     * completo
     * ResenaConUsuarioDTO respuesta = new ResenaConUsuarioDTO();
     * respuesta.setResena(resena);
     * respuesta.setUsuario(usuario);
     * * return respuesta;
     * }
     */
}