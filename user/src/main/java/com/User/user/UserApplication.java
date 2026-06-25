package com.User.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.User.user.model.TipoUsuario;
import com.User.user.repository.TipoUsuarioRepository;

@SpringBootApplication
@EnableFeignClients
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	@Bean
	public CommandLineRunner inicializarDatos(TipoUsuarioRepository tipoUsuarioRepository) {
		return args -> {
			// Validamos si ya existen registros para no duplicar datos
		if (tipoUsuarioRepository.count() == 0) {
			System.out.println("====== INICIANDO CARGA DE ROLES ======");
			
			String[] roles = {"admin", "usuario"};
			for (int i = 0; i < roles.length; i++) {
				TipoUsuario tipoUsuario = new TipoUsuario();
				tipoUsuario.setIdTipoUsuario((long) (i + 1)); 
				tipoUsuario.setNombreTipoUsuario(roles[i]);
				tipoUsuarioRepository.save(tipoUsuario);
			}
				System.out.println("====== ROLES CARGADOS EXITOSAMENTE ======");
			} else {
				System.out.println("====== LOS ROLES YA EXISTEN EN LA BASE DE DATOS ======");
			}
		};
	}
}
