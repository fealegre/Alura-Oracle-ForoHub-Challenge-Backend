package com.alura.forohub.challenge.service;

import com.alura.forohub.challenge.model.Topico;
import com.alura.forohub.challenge.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Transactional
    public Topico guardarTopico(Topico topico) {
        if (topicoRepository.findByTituloAndMensaje(topico.getTitulo(), topico.getMensaje()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título/mensaje.");
        }
        return topicoRepository.save(topico);
    }

    public Page<Topico> traerTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }

    public Page<Topico> traerTopicosByCursoAndRangoFecha(String curso, LocalDateTime fechaInicio, LocalDateTime fechaFinal, Pageable pageable) {
        return topicoRepository.findAllByCursoAndFechaCreacionBetween(curso, fechaInicio, fechaFinal, pageable);
    }

    public Optional<Topico> traerTopicoById(Long id) {
        return topicoRepository.findById(id);
    }

    @Transactional
    public Topico actualizarTopico(Long id, Topico topicoActualizado) {
        Optional<Topico> topicoActualizarOpt = topicoRepository.findById(id);
        if (topicoActualizarOpt.isPresent()) {
            Topico topicoActualizar = topicoActualizarOpt.get();
            if (topicoRepository.findByTituloAndMensaje(topicoActualizado.getTitulo(), topicoActualizado.getMensaje()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un tópico con el mismo título/mensaje.");
            }
            topicoActualizar.setTitulo(topicoActualizado.getTitulo());
            topicoActualizar.setMensaje(topicoActualizado.getMensaje());
            topicoActualizar.setAutor(topicoActualizado.getAutor());
            topicoActualizar.setCurso(topicoActualizado.getCurso());
            topicoActualizar.setStatus(topicoActualizado.getStatus());
            return topicoRepository.save(topicoActualizar);
        } else {
            throw new IllegalArgumentException("Tópico no encontrado");
        }
    }

    @Transactional
    public void borrarTopico(Long id) {
        Optional<Topico> borrarTopicoOpt = topicoRepository.findById(id);
        if (borrarTopicoOpt.isPresent()) {
            topicoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tópico no encontrado");
        }
    }
}
