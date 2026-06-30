package com.User.user.controller;

import com.User.user.model.TipoUsuario;
import com.User.user.service.TipoUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipos-usuario")
public class TipoUsuarioController {

    private final TipoUsuarioService tipoUsuarioService;

    // Inyección por Constructor (Adiós @Autowired manual)
    public TipoUsuarioController(TipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    // Listar todos O buscar por nombre exacto si se pasa ?nombre=...
    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            try {
                return ResponseEntity.ok(tipoUsuarioService.buscarPorNombre(nombre));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
        return ResponseEntity.ok(tipoUsuarioService.listarTodos());
    }

    // Buscar tipo de usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tipoUsuarioService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody TipoUsuario tipoUsuario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuarioService.guardar(tipoUsuario));
        } catch (RuntimeException e) {
            // Devuelve el mensaje de error si el tipo de usuario ya existe
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}