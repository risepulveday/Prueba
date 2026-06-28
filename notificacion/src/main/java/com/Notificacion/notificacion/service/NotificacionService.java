package com.Notificacion.notificacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa aquí tu cliente Feign y tu DTO de Pago cuando los tengas creados:
// import com.Notificacion.notificacion.client.PagoFeignClient;
// import com.Notificacion.notificacion.dto.PagoDTO;

import com.Notificacion.notificacion.model.NotificacionModel;
import com.Notificacion.notificacion.repository.NotificacionRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;
    // @Autowired
    // private PagoFeignClient pagoFeignClient; // Tu cliente Feign para conectar
    // con el microservicio de pagos

    // 1. Crear o Guardar una notificacion
    public NotificacionModel crearNotificacion(NotificacionModel notificacion) {
        // Aquí podrías agregar lógica antes de guardar, por ejemplo, validar si el
        // idPago realmente existe usando Feign
        return notificacionRepository.save(notificacion);
    }

    // 2. Obtener todas las notificacions
    public List<NotificacionModel> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    // 3. Obtener una notificacion por ID
    public Optional<NotificacionModel> obtenerPorId(Long id) {
        return notificacionRepository.findById(id);
    }

    // 4. Actualizar una notificacion
    public NotificacionModel actualizarNotificacion(Long id, NotificacionModel notificacionDetalles) {
        return notificacionRepository.findById(id).map(notificacion -> {
            notificacion.setFecha(notificacionDetalles.getFecha());
            notificacion.setIdUsuario(notificacionDetalles.getIdUsuario());
            return notificacionRepository.save(notificacion);
        }).orElseThrow(() -> new RuntimeException("Notificacion no encontrada con el id: " + id));
    }

    // 5. Eliminar una notificacion
    public void eliminarNotificacion(Long id) {
        if (notificacionRepository.existsById(id)) {
            notificacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Notificacion no encontrada con el id: " + id);
        }
    }

    // 6. Buscar notificacions por fecha
    public List<NotificacionModel> buscarPorFecha(Date fecha) {
        return notificacionRepository.findByFecha(fecha);
    }

    // 7. Buscar notificacions por usuario
    public List<NotificacionModel> buscarPorUsuario(Long idUsuario) {
        return notificacionRepository.findByIdUsuario(idUsuario);
    }

}