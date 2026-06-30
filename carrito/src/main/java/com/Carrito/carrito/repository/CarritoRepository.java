package com.Carrito.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Carrito.carrito.model.CarritoModel;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<CarritoModel, Long> {
    // Busca el carrito de un usuario en específico
    Optional<CarritoModel> findByIdUsuario(Long idUsuario);
}