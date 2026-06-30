package com.Dispositivo.dispositivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dispositivo.dispositivo.model.DispositivoModel;

import java.sql.Date;
import java.util.List;

@Repository
public interface DispositivoRepository extends JpaRepository<DispositivoModel, Long> {

    // Busca dispositivos que coincidan exactamente con una fecha
    List<DispositivoModel> findByFecha(Date fecha);

    // Busca dispositivos que coincidan exactamente con un total
    List<DispositivoModel> findByTotal(Integer total);
}