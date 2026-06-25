package com.User.user.repository;

import com.User.user.model.TipoUsuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {

    // Buscar tipo de usuario por nombre exacto (útil para validar duplicados antes de guardar)
    Optional<TipoUsuario> findByNombreTipoUsuarioIgnoreCase(String nombreTipoUsuario);
}