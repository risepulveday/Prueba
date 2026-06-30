package com.Dispositivo.dispositivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DispositivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DispositivoApplication.class, args);
	}
}