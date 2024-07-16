package com.alura.forohub.challenge.repository;

import com.alura.forohub.challenge.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
    Page<Topico> findAllByCursoAndFechaCreacionBetween(String curso, LocalDateTime fechaInicio, LocalDateTime fechaFinal, Pageable pageable);
}

