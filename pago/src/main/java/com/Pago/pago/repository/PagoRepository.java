package com.Pago.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Pago.pago.model.PagoModel;

import java.sql.Date;
import java.util.List;

public interface PagoRepository extends JpaRepository<PagoModel, Long> {

    // 🔍 Busca pagos que coincidan exactamente con la propiedad fechaPago del
    // modelo
    List<PagoModel> findByFechaPago(Date fechaPago);
}