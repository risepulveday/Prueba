package com.Oferta.oferta;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Oferta.oferta.model.OfertaModel;
import com.Oferta.oferta.repository.OfertaRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    private final OfertaRepository ofertaRepository;

    // Inyección por constructor limpia
    DataLoader(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar datos en tu entorno de desarrollo
        if (ofertaRepository.count() > 0) {
            System.out.println("La base de datos ya contiene ofertas. Saltando inicialización de Faker.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando ofertas de videojuegos con Datafaker...");

        // Generamos 15 ofertas de prueba
        for (int i = 0; i < 15; i++) {
            OfertaModel oferta = new OfertaModel();

            // 1. idOferta: No lo seteamos. Al tener @GeneratedValue(strategy = GenerationType.IDENTITY),
            // la base de datos lo autoincrementará (1, 2, 3...).

            // 2. descuento: Genera un porcentaje de descuento típico (ej. 10%, 15%, 25%, 50%, 75%, 80%)
            // Usamos un arreglo de ofertas comunes en juegos para que los datos tengan sentido comercial
            Integer[] descuentosComunes = {10, 15, 20, 25, 40, 50, 60, 75, 80, 85};
            Integer descuentoAleatorio = faker.options().nextElement(descuentosComunes);
            oferta.setDescuento(descuentoAleatorio);

            // 3. idJuego: Un ID aleatorio entre 1 y 100 para simular la relación vía Feign 
            // con el catálogo de juegos
            oferta.setIdJuego(faker.random().nextLong(1, 101));

            // Guardamos en la base de datos
            ofertaRepository.save(oferta);
        }

        System.out.println("¡Se insertaron 15 ofertas de juegos exitosamente!");
    }
}