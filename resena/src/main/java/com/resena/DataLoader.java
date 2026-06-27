package com.resena;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.resena.model.ResenaModel;
import com.resena.repository.ResenaRepository;

//import java.util.Random;
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    private final ResenaRepository resenaRepository;

    DataLoader(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    } // Corregido en minúscula por convención

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar datos si la base de datos ya está poblada
        if (resenaRepository.count() > 0) {
            System.out.println("La base de datos ya contiene resenas. Saltando inicialización de Faker.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando resenas de prueba con Datafaker...");

        // Generamos 20 resenas aleatorias
        for (int i = 0; i < 20; i++) {
            ResenaModel resena = new ResenaModel();

            // 1.Calificacion numero random entre 1 y 5
            resena.setCalificacion(faker.number().numberBetween(1, 5));

            // 2. Fecha: Una fecha aleatoria entre 1 y 5 años al pasado
            resena.setFecha(java.sql.Date.valueOf(faker.timeAndDate().birthday(1, 5)));

            // 3. Comentario: Un texto aleatorio
            resena.setComentario(faker.lorem().sentence());

            // 4. ID de usuario: Conectado con feing usuario.
            // resena.setIdUsuario(faker.number().numberBetween(1, 100));

            // Guardamos en la base de datos
            resenaRepository.save(resena);
        }

        System.out.println("¡Se insertaron 20 resenas falsas exitosamente!");
    }
}