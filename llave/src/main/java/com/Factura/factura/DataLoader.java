package com.Factura.factura;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Factura.factura.model.FacturaModel;
import com.Factura.factura.repository.FacturaRepository;

//import java.util.Random;
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private FacturaRepository facturaRepository; // Corregido en minúscula por convención

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar datos si la base de datos ya está poblada
        if (facturaRepository.count() > 0) {
            System.out.println("La base de datos ya contiene facturas. Saltando inicialización de Faker.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando facturas de prueba con Datafaker...");

        // Generamos 20 facturas aleatorias
        for (int i = 0; i < 20; i++) {
            FacturaModel factura = new FacturaModel();
            
            // 1. Total: Un monto aleatorio entre 10 y 5000
            factura.setTotal(faker.number().numberBetween(10, 5000));
            
            // 2. Fecha: Una fecha aleatoria entre 1 y 5 años al pasado
            factura.setFecha(java.sql.Date.valueOf(faker.timeAndDate().birthday(1, 5))); 

            // 3. Estado de la orden: Aleatorio (true o false)
            factura.setEstadoOrden(faker.bool().bool());
            
            // 4. ID de pago: Un número aleatorio (simulando los IDs que vendrán de tu otro microservicio)
            factura.setIdPago(faker.random().nextLong(1,20)); // IDs de 4 dígitos

            // Guardamos en la base de datos
            facturaRepository.save(factura);
        }

        System.out.println("¡Se insertaron 20 facturas falsas exitosamente!");
    }
}