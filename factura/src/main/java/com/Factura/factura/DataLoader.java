package com.Factura.factura;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Factura.factura.model.FacturaModel;
import com.Factura.factura.repository.FacturaRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    private final FacturaRepository facturaRepository;

    DataLoader(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

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

            // 4. ID de pago:(Puerto 8085)
            factura.setIdPago(faker.random().nextLong(1, 20));

            // 5. ID de usuario:Usuarios (Puerto 8080)
            factura.setIdUsuario(faker.random().nextLong(1, 30));

            // 6. ID de carrito:Carritos (Puerto 8082)
            factura.setIdCarrito(faker.random().nextLong(1, 20));

            // Guardamos en la base de datos
            facturaRepository.save(factura);
        }

        System.out.println("¡Se insertaron 20 facturas falsas exitosamente!");
    }
}