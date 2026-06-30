package com.Oferta.oferta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Oferta.oferta.model.OfertaModel;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface OfertaRepository extends JpaRepository<OfertaModel, Long> {

    List<OfertaModel> findByDescuento(Integer descuento);

    // Query personalizada Busca si el juego tiene descuento HOY
    @Query("SELECT o FROM OfertaModel o WHERE o.idJuego = :idJuego AND :fechaActual BETWEEN o.fechaInicio AND o.fechaFin")
    Optional<OfertaModel> findOfertaActivaParaJuego(
            @Param("idJuego") Long idJuego,
            @Param("fechaActual") LocalDate fechaActual);
}