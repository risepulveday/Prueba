package com.Pago.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Pago.pago.model.MetodoPagoModel;

public interface MetodoPagoRepository extends JpaRepository<MetodoPagoModel, Long> {
}