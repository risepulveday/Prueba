package com.Resena.resena;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Resena.resena.model.ResenaModel;
import com.Resena.resena.repository.ResenaRepository;
import com.Resena.resena.client.UsuarioFeignClient;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    private final ResenaRepository resenaRepository;
    private final UsuarioFeignClient usuarioFeignClient; // Inyectamos Feign

    // Constructor con ambas dependencias
    DataLoader(ResenaRepository resenaRepository, UsuarioFeignClient usuarioFeignClient) {
        this.resenaRepository = resenaRepository;
        this.usuarioFeignClient = usuarioFeignClient;
    }

    @Override
    public void run(String... args) throws Exception {
        // Evita duplicar datos si la base de datos ya está poblada
        if (resenaRepository.count() > 0) {
            System.out.println("La base de datos ya contiene resenas. Saltando inicialización de Faker.");
            return;
        }

        System.out.println("Verificando conectividad con el Microservicio de Usuarios a través de Feign...");

        Long idUsuarioValido = 1L; // Usaremos el ID 1 por defecto para las pruebas

        try {
            // 🔄 Corregido: 'obtenerUsuarioPorId' en lugar de 'obtainUsuarioPorId'
            usuarioFeignClient.obtenerUsuarioPorId(idUsuarioValido);
            System.out.println("Conexión con Microservicio de Usuarios exitosa. Usando ID: " + idUsuarioValido);
        } catch (Exception e) {
            System.out.println(
                    "⚠️ ALERTA: El microservicio de usuarios respondió o está encendido, pero no se encontró un usuario con ID 1.");
            System.out.println(
                    "Para evitar que falle la validación @NotNull, asignaremos ID 1 de todos modos en memoria.");
        }

        Faker faker = new Faker();
        System.out.println("Generando resenas de prueba con Datafaker...");

        // Generamos 20 resenas aleatorias
        for (int i = 0; i < 20; i++) {
            ResenaModel resena = new ResenaModel();

            // 1. Calificacion numero random entre 1 y 5 (en Datafaker el límite superior es
            // exclusivo)
            resena.setCalificacion(faker.number().numberBetween(1, 6));

            // 2. Fecha: Una fecha aleatoria entre 1 y 5 años al pasado
            resena.setFecha(java.sql.Date.valueOf(faker.timeAndDate().birthday(1, 5)));

            // 3. Comentario: Un texto aleatorio
            resena.setComentario(faker.lorem().sentence());

            // 4. ID de usuario: Vinculado de forma segura
            resena.setIdUsuario(idUsuarioValido);

            // Guardamos en la base de datos local
            resenaRepository.save(resena);
        }

        System.out.println(
                "¡Se insertaron 20 resenas falsas exitosamente vinculadas al usuario ID " + idUsuarioValido + "!");
    }
}