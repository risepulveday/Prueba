package com.Carrito.carrito;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Carrito.carrito.model.CarritoModel;
import com.Carrito.carrito.model.CarritoItemModel;
import com.Carrito.carrito.repository.CarritoRepository;

import java.util.ArrayList;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    private final CarritoRepository carritoRepository;

    DataLoader(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (carritoRepository.count() > 0) {
            System.out.println("La base de datos ya contiene carritos. Saltando inicialización.");
            return;
        }

        Faker faker = new Faker();
        System.out.println("Generando carritos conectados a Juegos con Datafaker...");

        for (int i = 0; i < 20; i++) {
            CarritoModel carrito = new CarritoModel();
            carrito.setIdUsuario(faker.random().nextLong(1, 30));
            carrito.setPrecioTotal(faker.number().numberBetween(5000, 120000));
            carrito.setItems(new ArrayList<>());

            int cantidadItems = faker.number().numberBetween(1, 4);
            for (int j = 0; j < cantidadItems; j++) {
                CarritoItemModel item = new CarritoItemModel();
                item.setCantidad(faker.number().numberBetween(1, 3));

                // 🎮 Asigna un ID de juego aleatorio (Simulando juegos del catálogo)
                item.setIdJuego(faker.random().nextLong(1, 50));
                item.setCarrito(carrito);

                carrito.getItems().add(item);
            }

            carritoRepository.save(carrito);
        }

        System.out.println("¡Se insertaron 20 carritos con ítems de juegos exitosamente!");
    }
}