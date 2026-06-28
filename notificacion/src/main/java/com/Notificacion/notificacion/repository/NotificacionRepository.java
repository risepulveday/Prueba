package com.Notificacion.notificacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Notificacion.notificacion.model.NotificacionModel;

import java.sql.Date;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<NotificacionModel, Long> {

    // Busca notificacions que coincidan exactamente con una fecha
    List<NotificacionModel> findByFecha(Date fecha);

    // Busca notificacions que coincidan exactamente con un usuario ID
    List<NotificacionModel> findByIdUsuario(Long idUsuario);
}