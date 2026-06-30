package com.Dispositivo.dispositivo.service;

import org.springframework.stereotype.Service;

import com.Dispositivo.dispositivo.dto.UsuarioDTO;
import com.Dispositivo.dispositivo.model.DispositivoModel;
import com.Dispositivo.dispositivo.repository.DispositivoRepository;
import com.Dispositivo.dispositivo.client.UsuarioFeignClient;
import java.util.List;
import java.util.Optional;

@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;
    private final UsuarioFeignClient usuarioFeignClient; // ✅ CORREGIDO: Se añade la variable que faltaba

    // Inyección por constructor (Práctica recomendada para facilitar pruebas
    // unitarias)
    // ✅ CORREGIDO: Ahora recibe e inyecta ambos componentes requeridos
    public DispositivoService(DispositivoRepository dispositivoRepository, UsuarioFeignClient usuarioFeignClient) {
        this.dispositivoRepository = dispositivoRepository;
        this.usuarioFeignClient = usuarioFeignClient;
    }

    // 1. Registrar un nuevo dispositivo con Validación DTO
    public DispositivoModel registrarDispositivo(DispositivoModel dispositivo) {
        try {
            // ✨ VALIDACIÓN: Intenta traer el DTO del usuario
            UsuarioDTO usuario = usuarioFeignClient.obtenerUsuarioPorId(dispositivo.getIdUsuario());
            if (usuario == null) {
                throw new RuntimeException("El usuario con ID " + dispositivo.getIdUsuario() + " no es válido.");
            }
        } catch (Exception e) {
            // Si el otro microservicio responde 404 o está caído, entra aquí:
            throw new RuntimeException("No se pudo registrar el dispositivo: El usuario con ID "
                    + dispositivo.getIdUsuario() + " no existe o el servicio de Usuarios no responde.");
        }

        return dispositivoRepository.save(dispositivo);
    }

    // 2. Obtener todos los dispositivos registrados (Usado por V1 y V2)
    public List<DispositivoModel> obtenerTodos() {
        return dispositivoRepository.findAll();
    }

    // 3. Buscar un dispositivo por su ID (Retorna un Optional para que el
    // Controlador V2 maneje el .orElseThrow)
    public Optional<DispositivoModel> obtenerPorId(Long id) {
        return dispositivoRepository.findById(id);
    }

    // 4. Obtener todos los dispositivos asociados a un usuario específico
    public List<DispositivoModel> obtenerPorUsuario(Long idUsuario) {
        return dispositivoRepository.findByIdUsuario(idUsuario);
    }

    // 5. Actualizar validando con DTO si cambia el dueño
    public DispositivoModel actualizarDispositivo(Long id, DispositivoModel detalles) {
        return dispositivoRepository.findById(id).map(dispositivo -> {

            try {
                // ✨ VALIDACIÓN: Comprobamos el DTO para el nuevo idUsuario asignado
                // ✅ CORREGIDO: De "obtainerUsuarioPorId" a "obtenerUsuarioPorId"
                UsuarioDTO usuario = usuarioFeignClient.obtenerUsuarioPorId(detalles.getIdUsuario());
                if (usuario == null) {
                    throw new RuntimeException("Usuario no válido.");
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al validar el propietario: El usuario con ID "
                        + detalles.getIdUsuario() + " no existe.");
            }

            dispositivo.setNombreDispositivo(detalles.getNombreDispositivo());
            dispositivo.setProcesador(detalles.getProcesador());
            dispositivo.setPuntajeProcesador(detalles.getPuntajeProcesador());
            dispositivo.setGrafica(detalles.getGrafica());
            dispositivo.setPuntajeGrafica(detalles.getPuntajeGrafica());
            dispositivo.setRam(detalles.getRam());
            dispositivo.setEspacio(detalles.getEspacio());
            dispositivo.setIdUsuario(detalles.getIdUsuario());
            return dispositivoRepository.save(dispositivo);
        }).orElseThrow(() -> new RuntimeException("Dispositivo no encontrado con el id: " + id));
    }

    // 6. Eliminar permanentemente un dispositivo del inventario
    public void eliminarDispositivo(Long id) {
        if (dispositivoRepository.existsById(id)) {
            dispositivoRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar: Dispositivo no encontrado con el id: " + id);
        }
    }
}