package com.User.user.service;

import com.User.user.model.Usuario;
import com.User.user.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
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