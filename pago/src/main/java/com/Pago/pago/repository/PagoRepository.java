package com.Pago.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Pago.pago.model.PagoModel;

import java.sql.Date;
import java.util.List;

public interface PagoRepository extends JpaRepository<PagoModel, Long> {

    // Busca pagos que coincidan exactamente con una fecha
    List<PagoModel> findByFecha(Date fecha);

    // Busca pagos que coincidan exactamente con un total
    List<PagoModel> findByTotal(Integer total);
}