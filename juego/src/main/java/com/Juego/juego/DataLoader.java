package com.Juego.juego;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Juego.juego.model.JuegoModel;
import com.Juego.juego.model.CategoriaModel;
import com.Juego.juego.repository.JuegoRepository;

import java.util.List;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    private final JuegoRepository juegoRepository;

    public DataLoader(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (juegoRepository.count() > 0) {
            System.out.println("La base de datos de juegos ya contiene datos. Saltando Faker.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando catálogo inicial de videojuegos y categorías...");

        List<String> categoriasDisponibles = List.of("Acción", "RPG", "Aventura", "Estrategia", "Deportes",
                "Simulación");

        List<String> titulosFamosos = List.of(
                "The Legend of Zelda: Tears of the Kingdom", "Elden Ring", "Cyberpunk 2077",
                "Minecraft", "Grand Theft Auto V", "Red Dead Redemption 2", "Resident Evil 4 Remake");

        // 1. Cargar títulos famosos con categorías aleatorias
        for (String titulo : titulosFamosos) {
            CategoriaModel categoria = new CategoriaModel();
            categoria.setNombreCategoria(faker.options().nextElement(categoriasDisponibles));

            JuegoModel juego = new JuegoModel();
            juego.setNombreJuego(titulo);
            juego.setPrecioJuego(faker.number().numberBetween(19990, 59990));
            juego.setStock(faker.number().numberBetween(5, 50));
            juego.setCategoria(categoria);
            juegoRepository.save(juego);
        }

        // 2. Rellenar con más juegos de Datafaker usando .videoGame().title()
        for (int i = 0; i < 43; i++) {
            CategoriaModel categoria = new CategoriaModel();
            categoria.setNombreCategoria(faker.options().nextElement(categoriasDisponibles));

            JuegoModel juego = new JuegoModel();
            juego.setNombreJuego(faker.videoGame().title()); // Corregido con 'G' mayúscula
            juego.setPrecioJuego(faker.number().numberBetween(4990, 49990));
            juego.setStock(faker.number().numberBetween(0, 100));
            juego.setCategoria(categoria);
            juegoRepository.save(juego);
        }

        System.out.println("¡Se agregaron 50 videojuegos clasificados por categoría exitosamente!");
    }
}