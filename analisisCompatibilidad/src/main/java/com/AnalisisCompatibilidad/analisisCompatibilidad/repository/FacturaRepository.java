package com.Factura.factura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Factura.factura.model.FacturaModel;

import java.sql.Date;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaModel, Long> {

    // Busca facturas que coincidan exactamente con una fecha
    List<FacturaModel> findByFecha(Date fecha);

    // Busca facturas que coincidan exactamente con un total
    List<FacturaModel> findByTotal(Integer total);
}