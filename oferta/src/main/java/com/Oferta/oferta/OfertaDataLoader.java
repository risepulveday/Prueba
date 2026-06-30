package com.Oferta.oferta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.LocalDate; // ✅ ¡Este import soluciona el problema de compilación!

import com.Oferta.oferta.model.OfertaModel;
import com.Oferta.oferta.repository.OfertaRepository;

@Profile("dev")
@Component
public class OfertaDataLoader implements CommandLineRunner {

    private final OfertaRepository ofertaRepository;

    // Inyección por constructor (Práctica recomendada)
    public OfertaDataLoader(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 🔍 Evita duplicar datos si la base de datos ya tiene registros
        if (ofertaRepository.count() == 0) {

            // 1. Oferta para el Juego con ID 1 (Ej: Elden Ring - 20% OFF)
            OfertaModel oferta1 = new OfertaModel();
            oferta1.setDescuento(20);
            oferta1.setIdJuego(1L);
            oferta1.setFechaInicio(LocalDate.now()); // Inicia hoy
            oferta1.setFechaFin(LocalDate.now().plusMonths(1)); // Expira en un mes

            // 2. Oferta para el Juego con ID 2 (Ej: Minecraft - 50% OFF)
            OfertaModel oferta2 = new OfertaModel();
            oferta2.setDescuento(50);
            oferta2.setIdJuego(2L);
            oferta2.setFechaInicio(LocalDate.now().minusDays(5)); // Empezó hace 5 días
            oferta2.setFechaFin(LocalDate.now().plusDays(5)); // Termina en 5 días

            // Guardar en la base de datos
            ofertaRepository.save(oferta1);
            ofertaRepository.save(oferta2);

            System.out.println("✨ [Data Loader] Se insertaron 2 ofertas de prueba exitosamente.");
        } else {
            System.out.println("ℹ️ [Data Loader] La tabla de ofertas ya contiene datos. Saltando inicialización.");
        }
    }
}