package com.Llave.llave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Llave.llave.model.LlaveModel;

import java.sql.Date;
import java.util.List;

@Repository
public interface LlaveRepository extends JpaRepository<LlaveModel, Long> {

    // Busca llaves que coincidan exactamente con un juego ID
    List<LlaveModel> findByIdJuego(Long idJuego);
}