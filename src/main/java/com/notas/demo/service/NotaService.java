package com.notas.demo.service;



import com.notas.demo.model.NotaModel;
import com.notas.demo.repository.I_NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotaService { // Cambiado a NotaService

    @Autowired
    private I_NotaRepository notaRepository; // Cambiado a NotaRepository

    // Obtener todas las notas activas (no archivadas)
    public List<NotaModel> obtenerNotasActivas() {
        return notaRepository.findByArchivadaFalse(); // Cambiado el nombre del método
    }

    // Obtener todas las notas archivadas
    public List<NotaModel> obtenerNotasArchivadas() {
        return notaRepository.findByArchivadaTrue(); // Cambiado el nombre del método
    }

    // Crear una nueva nota
    public NotaModel crearNota(NotaModel nota) {
        return notaRepository.save(nota);
    }

    // Actualizar una nota existente
    public NotaModel actualizarNota(Long id, NotaModel detallesNota) {
        Optional<NotaModel> optionalNota = notaRepository.findById(id);
        if (optionalNota.isPresent()) {
            NotaModel nota = optionalNota.get();
            nota.setTitulo(detallesNota.getTitulo());
            nota.setContenido(detallesNota.getContenido());
            return notaRepository.save(nota);
        }
        return null;
    }

    // Archivar una nota (cambiar estado a archivada)
    public NotaModel archivarNota(Long id) {
        Optional<NotaModel> optionalNota = notaRepository.findById(id);
        if (optionalNota.isPresent()) {
            NotaModel nota = optionalNota.get();
            nota.setArchivada(true); // Cambiado a setArchivada
            return notaRepository.save(nota);
        }
        return null;
    }

    // Desarchivar una nota (cambiar estado a activa)
    public NotaModel desarchivarNota(Long id) {
        Optional<NotaModel> optionalNota = notaRepository.findById(id);
        if (optionalNota.isPresent()) {
            NotaModel nota = optionalNota.get();
            nota.setArchivada(false); // Cambiado a setArchivada
            return notaRepository.save(nota);
        }
        return null;
    }

    // Eliminar una nota permanentemente
    public void eliminarNota(Long id) {
        notaRepository.deleteById(id);
    }

    // Obtener una nota específica por su ID
    public Optional<NotaModel> obtenerNotaPorId(Long id) {
        return notaRepository.findById(id);
    }
}