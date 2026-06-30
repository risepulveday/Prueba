package com.Juego.juego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Juego.juego.model.JuegoModel;

public interface JuegoRepository extends JpaRepository<JuegoModel, Long> {
}