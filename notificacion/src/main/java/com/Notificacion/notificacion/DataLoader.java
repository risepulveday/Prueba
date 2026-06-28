package com.Notificacion.notificacion;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Notificacion.notificacion.model.NotificacionModel;
import com.Notificacion.notificacion.repository.NotificacionRepository;

//import java.util.Random;
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private NotificacionRepository notificacionRepository; // Corregido en minúscula por convención

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar datos si la base de datos ya está poblada
        if (notificacionRepository.count() > 0) {
            System.out.println("La base de datos ya contiene notificacions. Saltando inicialización de Faker.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando notificacions de prueba con Datafaker...");

        // Generamos 20 notificacions aleatorias
        for (int i = 0; i < 20; i++) {
            NotificacionModel notificacion = new NotificacionModel();

            // 2. Fecha: Una fecha aleatoria entre 1 y 5 años al pasado
            notificacion.setFecha(java.sql.Date.valueOf(faker.timeAndDate().birthday(1, 5)));

            // 3. ID de usuario: Un número aleatorio (simulando los IDs que vendrán de tu
            // otro microservicio)
            notificacion.setIdUsuario(faker.random().nextLong(1, 20)); // IDs de 4 dígitos

            // Guardamos en la base de datos
            notificacionRepository.save(notificacion);
        }

        System.out.println("¡Se insertaron 20 notificacions falsas exitosamente!");
    }
}