package com.User.user.service;

import com.User.user.model.Usuario;
import com.User.user.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    // 1. Definimos el repositorio como final
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Retorna un Optional para que el Controlador pueda decidir si mandar un 404 de forma limpia
    public Optional<Usuario> obtenerPorIdOptional(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public Usuario guardar(Usuario usuario) {
        // Validación: Verificar si el correo ya está registrado
        if (usuarioRepository.findByGmailUsuario(usuario.getGmailUsuario()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya se encuentra registrado.");
        }
        return usuarioRepository.save(usuario);
    }
    
    // LISTAR POR NOMBRE (Búsqueda parcial e insensible a mayúsculas/minúsculas)
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreUsuarioContainingIgnoreCase(nombre);
    }

    // LISTAR POR ID DE TIPO DE USUARIO
    public List<Usuario> buscarPorTipoUsuario(Long idTipoUsuario) {
        return usuarioRepository.findByTipoUsuario_IdTipoUsuario(idTipoUsuario);
    }

    public Usuario validarGmail(Usuario usuario) {
        // Validación mejorada: Solo falla si el correo existe Y pertenece a OTRO usuario diferente
        Optional<Usuario> usuarioExistente = usuarioRepository.findByGmailUsuario(usuario.getGmailUsuario());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new RuntimeException("El correo electrónico ya se encuentra registrado por otro usuario.");
        }
        return usuarioRepository.save(usuario);
    }
}