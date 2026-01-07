package com.notas.demo.controller;


import com.notas.demo.model.NotaModel;

import com.notas.demo.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notas") // Cambiado a español para consistencia
public class NotaController { // Nombre de clase en español

    @Autowired
    private NotaService notaService; // Cambiado a NotaService

    // Obtener todas las notas activas (no archivadas)
    @GetMapping("/activas")
    public List<NotaModel> obtenerNotasActivas() {
        return notaService.obtenerNotasActivas();
    }

    // Obtener todas las notas archivadas
    @GetMapping("/archivadas")
    public List<NotaModel> obtenerNotasArchivadas() {
        return notaService.obtenerNotasArchivadas();
    }

    // Obtener una nota específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<NotaModel> obtenerNotaPorId(@PathVariable Long id) {
        Optional<NotaModel> nota = notaService.obtenerNotaPorId(id);
        return nota.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva nota
    @PostMapping
    public ResponseEntity<NotaModel> crearNota(@RequestBody NotaModel nota) {
        NotaModel notaCreada = notaService.crearNota(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(notaCreada);
    }

    // Actualizar una nota existente
    @PutMapping("/{id}")
    public ResponseEntity<NotaModel> actualizarNota(@PathVariable Long id, @RequestBody NotaModel detallesNota) {
        NotaModel notaActualizada = notaService.actualizarNota(id, detallesNota);
        if (notaActualizada != null) {
            return ResponseEntity.ok(notaActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    // Archivar una nota (cambiar estado a archivada)
    @PatchMapping("/{id}/archivar")
    public ResponseEntity<NotaModel> archivarNota(@PathVariable Long id) {
        NotaModel notaArchivada = notaService.archivarNota(id);
        if (notaArchivada != null) {
            return ResponseEntity.ok(notaArchivada);
        }
        return ResponseEntity.notFound().build();
    }

    // Desarchivar una nota (cambiar estado a activa)
    @PatchMapping("/{id}/desarchivar")
    public ResponseEntity<NotaModel> desarchivarNota(@PathVariable Long id) {
        NotaModel notaDesarchivada = notaService.desarchivarNota(id);
        if (notaDesarchivada != null) {
            return ResponseEntity.ok(notaDesarchivada);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar una nota permanentemente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNota(@PathVariable Long id) {
        notaService.eliminarNota(id);
        return ResponseEntity.noContent().build();
    }
}