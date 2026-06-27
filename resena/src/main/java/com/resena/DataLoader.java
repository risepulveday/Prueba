package com.resena;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.resena.model.ResenaModel;
import com.resena.repository.ResenaRepository;

//import java.util.Random;
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ResenaRepository resenaRepository; // Corregido en minúscula por convención

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

            // 1. Total: Un monto aleatorio entre 10 y 5000
            resena.setTotal(faker.number().numberBetween(10, 5000));

            // 2. Fecha: Una fecha aleatoria entre 1 y 5 años al pasado
            resena.setFecha(java.sql.Date.valueOf(faker.timeAndDate().birthday(1, 5)));

            // 3. Estado de la orden: Aleatorio (true o false)
            resena.setEstadoOrden(faker.bool().bool());

            // 4. ID de pago: Un número aleatorio (simulando los IDs que vendrán de tu otro
            // microservicio)
            resena.setIdPago(faker.random().nextLong(1, 20)); // IDs de 4 dígitos

            // Guardamos en la base de datos
            resenaRepository.save(resena);
        }

        System.out.println("¡Se insertaron 20 resenas falsas exitosamente!");
    }
}