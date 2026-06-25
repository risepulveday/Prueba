package com.User.user.controller;

import com.User.user.model.Usuario;
import com.User.user.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Modificado para listar todos O filtrar por nombre si se pasa el parámetro ?nombre=...
    @GetMapping
    public List<Usuario> listar(@RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            return usuarioService.buscarPorNombre(nombre);
        }
        return usuarioService.listarTodos();
    }

    // Buscar un usuario específico por su ID único
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // NUEVO: Filtrar usuarios por el ID de su Tipo de Usuario (Ej: /api/usuarios/tipo/1)
    @GetMapping("/tipo/{idTipoUsuario}")
    public List<Usuario> listarPorTipoUsuario(@PathVariable Long idTipoUsuario) {
        return usuarioService.buscarPorTipoUsuario(idTipoUsuario);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
        } catch (RuntimeException e) {
            // Devuelve el mensaje de error si el correo ya existe
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}