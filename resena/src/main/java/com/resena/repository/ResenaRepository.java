package com.resena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resena.model.ResenaModel;

import java.sql.Date;
import java.util.List;

public interface ResenaRepository extends JpaRepository<ResenaModel, Long> {

    // 1. Permite buscar reseñas por su fecha
    List<ResenaModel> findByFecha(Date fecha);

    // 2. SOLUCIÓN: Declara la búsqueda por calificación (Estrellas)
    List<ResenaModel> findByCalificacion(Integer calificacion);

    // 3. SOLUCIÓN: Declara la búsqueda por el ID del usuario remoto
    List<ResenaModel> findByIdUsuario(Long idUsuario);
}