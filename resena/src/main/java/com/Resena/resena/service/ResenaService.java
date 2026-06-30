package com.Resena.resena.service;

import org.springframework.stereotype.Service;

import com.Resena.resena.client.UsuarioFeignClient;
import com.Resena.resena.dto.ResenaConUsuarioDTO;
import com.Resena.resena.dto.UsuarioDTO;

import com.Resena.resena.model.ResenaModel;
import com.Resena.resena.repository.ResenaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final UsuarioFeignClient usuarioFeignClient; // Cliente Feign integrado

    // Inyección limpia por constructor (evita cualquier alerta o warning de VS
    // Code)
    public ResenaService(ResenaRepository resenaRepository, UsuarioFeignClient usuarioFeignClient) {
        this.resenaRepository = resenaRepository;
        this.usuarioFeignClient = usuarioFeignClient;
    }

    // 1. Crear o Guardar una reseña (Con validación remota mediante Feign)
    public ResenaModel crearResena(ResenaModel resena) {
        try {
            // 🔍 Solo ejecutamos la llamada. Si el usuario no existe, Feign lanzará una
            // excepción.
            usuarioFeignClient.obtenerUsuarioPorId(resena.getIdUsuario());

            // Si la llamada pasa sin errores, guardamos la reseña con total seguridad
            return resenaRepository.save(resena);

        } catch (Exception e) {
            // Si el usuario remoto no existe o el servicio está apagado, frena la inserción
            throw new RuntimeException("Error al registrar reseña: El usuario con ID "
                    + resena.getIdUsuario() + " no existe en el sistema o el servidor no responde.");
        }
    }

    // 2. Obtener todas las reseñas
    public List<ResenaModel> obtenerTodas() {
        return resenaRepository.findAll();
    }

    // 3. Obtener una reseña por ID
    public Optional<ResenaModel> obtenerPorId(Long id) {
        return resenaRepository.findById(id);
    }

    // 4. Actualizar una reseña
    public ResenaModel actualizarResena(Long id, ResenaModel resenaDetalles) {
        return resenaRepository.findById(id).map(resena -> {
            resena.setCalificacion(resenaDetalles.getCalificacion());
            resena.setFecha(resenaDetalles.getFecha());
            resena.setComentario(resenaDetalles.getComentario());
            resena.setIdUsuario(resenaDetalles.getIdUsuario());
            return resenaRepository.save(resena);
        }).orElseThrow(() -> new RuntimeException("Reseña no encontrada con el id: " + id));
    }

    // 5. Eliminar una reseña
    public void eliminarResena(Long id) {
        if (resenaRepository.existsById(id)) {
            resenaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reseña no encontrada con el id: " + id);
        }
    }

    // 6. Buscar reseñas por fecha
    public List<ResenaModel> buscarPorFecha(Date fecha) {
        return resenaRepository.findByFecha(fecha);
    }

    // 7. Buscar reseñas por calificación
    public List<ResenaModel> buscarPorCalificacion(Integer calificacion) {
        return resenaRepository.findByCalificacion(calificacion);
    }

    // 8. Buscar reseñas asociadas a un usuario específico
    public List<ResenaModel> buscarPorUsuario(Long idUsuario) {
        return resenaRepository.findByIdUsuario(idUsuario);
    }

    // 9 Nuevo método para componer los datos de la reseña con su usuario remoto
    public ResenaConUsuarioDTO obtenerResenaConSuUsuario(Long idResena) {
        // 1. Buscamos la reseña localmente por su ID
        ResenaModel resena = resenaRepository.findById(idResena)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con el id: " + idResena));

        // 2. Consumimos el microservicio de usuarios usando Feign para traer los datos
        // del perfil
        UsuarioDTO usuario = usuarioFeignClient.obtenerUsuarioPorId(resena.getIdUsuario());

        // 3. Juntamos ambos objetos en el DTO compuesto
        ResenaConUsuarioDTO respuesta = new ResenaConUsuarioDTO();
        respuesta.setResena(resena);
        respuesta.setUsuario(usuario);

        return respuesta;
    }

}