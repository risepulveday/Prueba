package com.Carrito.carrito.service;

import org.springframework.stereotype.Service;
import com.Carrito.carrito.client.JuegoFeignClient;
import com.Carrito.carrito.dto.JuegoDTO;
import com.Carrito.carrito.model.CarritoModel;
import com.Carrito.carrito.model.CarritoItemModel;
import com.Carrito.carrito.repository.CarritoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final JuegoFeignClient juegoFeignClient;

    public CarritoService(CarritoRepository carritoRepository, JuegoFeignClient juegoFeignClient) {
        this.carritoRepository = carritoRepository;
        this.juegoFeignClient = juegoFeignClient;
    }

    // 🎮 1. Guardar carrito calculando precios reales desde el microservicio de
    // Juegos vía Feign
    public CarritoModel guardarYCalcularCarrito(CarritoModel carrito) {
        int totalAcumulado = 0;

        if (carrito.getItems() != null) {
            for (CarritoItemModel item : carrito.getItems()) {
                item.setCarrito(carrito); // Asegura el vínculo bidireccional de Jackson

                try {
                    // 🔌 Consumimos el puerto 8084 de Juegos
                    JuegoDTO juego = juegoFeignClient.obtenerJuegoPorId(item.getIdJuego());
                    if (juego != null) {
                        totalAcumulado += juego.getPrecioJuego() * item.getCantidad();
                    }
                } catch (Exception e) {
                    System.err.println("No se pudo obtener el precio del juego con ID " + item.getIdJuego()
                            + ", usando cálculo manual o fallback.");
                }
            }
        }

        if (totalAcumulado > 0) {
            carrito.setPrecioTotal(totalAcumulado);
        }

        return carritoRepository.save(carrito);
    }

    // 📋 2. Obtener todos los carritos (Requerido por CarritoController y V2)
    public List<CarritoModel> obtenerTodas() {
        return carritoRepository.findAll();
    }

    // 🔍 3. Obtener un carrito por ID (Requerido por CarritoController y V2)
    public Optional<CarritoModel> obtenerPorId(Long id) {
        return carritoRepository.findById(id);
    }

    // 👤 4. Obtener el carrito de un usuario específico (Requerido por
    // CarritoController)
    public Optional<CarritoModel> obtenerPorIdUsuario(Long idUsuario) {
        return carritoRepository.findByIdUsuario(idUsuario);
    }

    // ❌ 5. Eliminar un carrito y sus ítems en cascada (Requerido por
    // CarritoController)
    public void eliminarCarrito(Long id) {
        if (carritoRepository.existsById(id)) {
            carritoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Carrito no encontrado con el id: " + id);
        }
    }
}