package com.Oferta.oferta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Oferta.oferta.model.OfertaModel;

import java.util.List;

@Repository
public interface OfertaRepository extends JpaRepository<OfertaModel, Long> {

    // Busca ofertas que coincidan exactamente con un descuento
    List<OfertaModel> findByDescuento(Integer descuento);
}