package com.Dispositivo.dispositivo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.Dispositivo.dispositivo.model.DispositivoModel;
import com.Dispositivo.dispositivo.repository.DispositivoRepository;

@Profile("dev")
@Component
public class DispositivoDataLoader implements CommandLineRunner {

    private final DispositivoRepository dispositivoRepository;

    public DispositivoDataLoader(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (dispositivoRepository.count() == 0) {

            // 1. Dispositivo Gama Media (Usuario ID: 1)
            DispositivoModel pcMedia = new DispositivoModel();
            pcMedia.setNombreDispositivo("PC de Escritorio - Oficina/Gaming");
            pcMedia.setProcesador("Intel Core i5-10400F");
            pcMedia.setPuntajeProcesador(12000); // ⭐ Puntaje de rendimiento
            pcMedia.setGrafica("NVIDIA GTX 1660 Super");
            pcMedia.setPuntajeGrafica(9000); // ⭐ Puntaje de rendimiento
            pcMedia.setRam(16);
            pcMedia.setEspacio(500);
            pcMedia.setIdUsuario(1L);

            // 2. Dispositivo Gama Alta (Usuario ID: 2)
            DispositivoModel pcAlta = new DispositivoModel();
            pcAlta.setNombreDispositivo("Notebook Master Race");
            pcAlta.setProcesador("AMD Ryzen 7 5800X");
            pcAlta.setPuntajeProcesador(25000); // ⭐ Más potente
            pcAlta.setGrafica("NVIDIA RTX 3070");
            pcAlta.setPuntajeGrafica(22000); // ⭐ Más potente
            pcAlta.setRam(32);
            pcAlta.setEspacio(1000);
            pcAlta.setIdUsuario(2L);

            dispositivoRepository.save(pcMedia);
            dispositivoRepository.save(pcAlta);

            System.out.println("✨ [Data Loader] Se cargaron 2 dispositivos de prueba con sus puntajes.");
        }
    }
}