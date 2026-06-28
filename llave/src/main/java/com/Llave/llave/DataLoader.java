package com.Llave.llave;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Llave.llave.model.LlaveModel;
import com.Llave.llave.repository.LlaveRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    private final LlaveRepository llaveRepository;

    // Inyección limpia por constructor
    DataLoader(LlaveRepository llaveRepository) {
        this.llaveRepository = llaveRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar datos si ya existen registros
        if (llaveRepository.count() > 0) {
            System.out.println("La base de datos ya contiene llaves. Saltando inicialización de Faker.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando llaves de activación (CD Keys) con Datafaker...");

        // Generamos un lote masivo de 30 llaves de prueba para simular stock en el sistema
        for (int i = 0; i < 30; i++) {
            LlaveModel llave = new LlaveModel();

            // 1. idLlave: No se setea manualmente. Al estar anotado con @GeneratedValue,
            // la base de datos se encarga de autoincrementar el contador (1, 2, 3...).

            // 2. idJuego: Simulamos la asociación asignando la llave a un juego del catálogo
            // (IDs aleatorios entre 1 y 10, coincidiendo con la escala que usamos en ofertas).
            llave.setIdJuego(faker.random().nextLong(1, 10));

            // Guardamos el registro en la base de datos
            llaveRepository.save(llave);
        }

        System.out.println("¡Se insertaron 30 llaves de activación exitosamente!");
    }
}