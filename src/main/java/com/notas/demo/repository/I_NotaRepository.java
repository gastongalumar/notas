package com.notas.demo.repository;



import com.notas.demo.model.NotaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface I_NotaRepository extends JpaRepository<NotaModel, Long> {
    // Notas activas (no archivadas)
    List<NotaModel> findByArchivadaFalse();

    // Notas archivadas
    List<NotaModel> findByArchivadaTrue();
}