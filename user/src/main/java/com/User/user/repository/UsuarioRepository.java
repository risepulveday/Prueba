package com.User.user.repository;

import com.User.user.model.TipoUsuario;
import com.User.user.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // El que ya usabas para la validación
    Optional<Usuario> findByGmailUsuario(String gmailUsuario);

    // 1. Buscar por nombre exacto
    List<Usuario> findByNombreUsuario(String nombreUsuario);

    // 2. Buscar por nombre que contenga una palabra (ideal para buscadores)
    List<Usuario> findByNombreUsuarioContainingIgnoreCase(String nombreUsuario);

    // 3. Buscar usuarios por su Tipo de Usuario (Pasando el objeto)
    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);

    // 4. Buscar usuarios por el ID del Tipo de Usuario (Más directo y cómodo)
    List<Usuario> findByTipoUsuario_IdTipoUsuario(Long idTipoUsuario);
}