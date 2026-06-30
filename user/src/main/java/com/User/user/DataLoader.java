package com.User.user;

import com.User.user.model.Usuario;
import com.User.user.model.TipoUsuario;
import com.User.user.repository.UsuarioRepository;
import com.User.user.repository.TipoUsuarioRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//import java.util.Random;
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;

    private final TipoUsuarioRepository tipoUsuarioRepository;

    DataLoader(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        // Random random = new Random();

        // 1. Crear los roles fijos de forma segura (Aprovechando el auto_increment)
        String[] roles = { "admin", "usuario" };
        List<TipoUsuario> rolesGuardados = new ArrayList<>();

        for (String rol : roles) {
            TipoUsuario tipoUsuario = new TipoUsuario();
            // NO usamos setIdTipoUsuario, la base de datos lo asigna automáticamente
            tipoUsuario.setNombreTipoUsuario(rol);
            rolesGuardados.add(tipoUsuarioRepository.save(tipoUsuario));
        }

        // 2. Generar Usuarios de prueba vinculados a esos roles usando Faker

        for (int i = 0; i < 10; i++) { // Genera 10 usuarios aleatorios
            Usuario usuario = new Usuario();

            usuario.setNombreUsuario(faker.name().firstName());
            usuario.setGmailUsuario(faker.internet().emailAddress());
            usuario.setContrasena(faker.lorem().characters(8));

            // Selecciona al azar uno de los roles reales guardados en la base de datos
            TipoUsuario rolAleatorio = faker.options().nextElement(rolesGuardados);
            usuario.setTipoUsuario(rolAleatorio);

            usuarioRepository.save(usuario);
        }
    }
}
