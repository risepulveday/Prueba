package com.Pago.pago;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Pago.pago.model.PagoModel;
import com.Pago.pago.repository.PagoRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    private final PagoRepository pagoRepository;

    DataLoader(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar datos si la base de datos ya está poblada
        if (pagoRepository.count() > 0) {
            System.out.println("La base de datos ya contiene pagos. Saltando inicialización de Faker.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando pagos de prueba con Datafaker...");

        // Generamos 20 pagos aleatorios
        for (int i = 0; i < 20; i++) {
            PagoModel pago = new PagoModel();

            // 1. idPago: Se deja nulo porque está anotado con @GeneratedValue(strategy =
            // GenerationType.IDENTITY)
            // Windows/MySQL se encargará de incrementarlo automáticamente.

            // 2. fechaPago: Usando la nueva API compatible con Datafaker 2.5.4+
            // Genera un instante aleatorio en los últimos 30 días y lo convierte a
            // java.sql.Date
            java.time.Instant instantePasado = faker.timeAndDate().past(30, java.util.concurrent.TimeUnit.DAYS);
            pago.setFechaPago(new java.sql.Date(instantePasado.toEpochMilli()));

            // 3. estadoPago: Aleatorio (true para pago aprobado, false para
            // rechazado/pendiente)
            pago.setEstadoPago(faker.bool().bool());

            // 4. metodoPago: Un entero aleatorio entre 1 y 4
            // (Simulando: 1=Tarjeta,2=Transferencia, 3=Efectivo, 4=PayPal)
            pago.setMetodoPago(faker.number().numberBetween(1, 5));

            // 5. idFactura: Un ID aleatorio largo entre 1 y 50 para simular la relación con
            // el microservicio de Facturas Vía Feign
            pago.setIdFactura(faker.random().nextLong(1, 51));

            // Guardamos en la base de datos
            pagoRepository.save(pago);
        }

        System.out.println("¡Se insertaron 20 pagos de prueba exitosamente!");
    }
}