package com.Dispositivo.dispositivo;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Dispositivo.dispositivo.model.DispositivoModel;
import com.Dispositivo.dispositivo.repository.DispositivoRepository;

//import java.util.Random;
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private DispositivoRepository dispositivoRepository; // Corregido en minúscula por convención

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar datos si la base de datos ya está poblada
        if (dispositivoRepository.count() > 0) {
            System.out.println("La base de datos ya contiene dispositivos. Saltando inicialización de Faker.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando dispositivos de prueba con Datafaker...");

        // Generamos 20 dispositivos aleatorias
        for (int i = 0; i < 20; i++) {
            DispositivoModel dispositivo = new DispositivoModel();
            
            // 1. Total: Un monto aleatorio entre 10 y 5000
            dispositivo.setTotal(faker.number().numberBetween(10, 5000));
            
            // 2. Fecha: Una fecha aleatoria entre 1 y 5 años al pasado
            dispositivo.setFecha(java.sql.Date.valueOf(faker.timeAndDate().birthday(1, 5))); 

            // 3. Estado de la orden: Aleatorio (true o false)
            dispositivo.setEstadoOrden(faker.bool().bool());
            
            // 4. ID de pago: Un número aleatorio (simulando los IDs que vendrán de tu otro microservicio)
            dispositivo.setIdPago(faker.random().nextLong(1,20)); // IDs de 4 dígitos

            // Guardamos en la base de datos
            dispositivoRepository.save(dispositivo);
        }

        System.out.println("¡Se insertaron 20 dispositivos falsas exitosamente!");
    }
}