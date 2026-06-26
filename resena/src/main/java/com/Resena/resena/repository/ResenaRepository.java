package com.Resena.resena.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Factura.factura.model.FacturaModel;
import com.Resena.resena.model.Resena;

import java.sql.Date;
import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<FacturaModel, Long> {

    // Busca facturas que coincidan exactamente con una fecha
    List<ResenaModel> findByFecha(Date fecha);

    // Busca facturas que coincidan exactamente con un total
    List<ResenaModel> findByTotal(Integer total);
}