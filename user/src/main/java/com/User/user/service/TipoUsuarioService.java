package com.User.user.service;

import com.User.user.model.TipoUsuario;
import com.User.user.repository.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoUsuarioService {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    
    // Listar todos los tipos de usuarios
    public List<TipoUsuario> listarTodos() {
        return tipoUsuarioRepository.findAll();
    }
    //busca los tipos de usuarios por id
    public TipoUsuario buscarPorId(Long id) {
        return tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado con ID: " + id));
    }

    // BUSCAR POR NOMBRE EXACTO
    public TipoUsuario buscarPorNombre(String nombre) {
        return tipoUsuarioRepository.findByNombreTipoUsuarioIgnoreCase(nombre)
                .orElseThrow(() -> new RuntimeException("No se encontró el tipo de usuario: " + nombre));
    }

    public TipoUsuario guardar(TipoUsuario tipoUsuario) {
        // Evita crear duplicados como "ADMIN" y "admin"
        if (tipoUsuarioRepository.findByNombreTipoUsuarioIgnoreCase(tipoUsuario.getNombreTipoUsuario()).isPresent()) {
            throw new RuntimeException("Este tipo de usuario ya existe.");
        }
        return tipoUsuarioRepository.save(tipoUsuario);
    }
}