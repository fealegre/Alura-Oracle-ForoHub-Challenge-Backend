package com.alura.forohub.challenge.controller;

import com.alura.forohub.challenge.DTO.TopicoRequest;
import com.alura.forohub.challenge.model.Topico;
import com.alura.forohub.challenge.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<Topico> crearTopico(@Valid @RequestBody TopicoRequest topicoRequest) {
        Topico topico = new Topico();
        topico.setTitulo(topicoRequest.getTitulo());
        topico.setMensaje(topicoRequest.getMensaje());
        topico.setAutor(topicoRequest.getAutor());
        topico.setCurso(topicoRequest.getCurso());
        topico.setStatus(topicoRequest.getStatus());

        Topico guardadoTopico = topicoService.guardarTopico(topico);
        return new ResponseEntity<>(guardadoTopico, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Topico>> traerTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        Page<Topico> topicos = topicoService.traerTopicos(pageable);
        return new ResponseEntity<>(topicos, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Topico>> traerTopicosByCursoAndRangoFecha(
            @RequestParam String curso,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFinal,
            @PageableDefault(size = 10, sort = "createdDate") Pageable pageable) {
        Page<Topico> topicos = topicoService.traerTopicosByCursoAndRangoFecha(curso, fechaInicio, fechaFinal, pageable);
        return new ResponseEntity<>(topicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> traerTopicoById(@PathVariable Long id) {
        Optional<Topico> topico = topicoService.traerTopicoById(id);
        if (topico.isPresent()) {
            return new ResponseEntity<>(topico.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @Valid @RequestBody TopicoRequest topicoRequest) {
        Topico topicoActualizado = new Topico();
        topicoActualizado.setTitulo(topicoRequest.getTitulo());
        topicoActualizado.setMensaje(topicoRequest.getMensaje());
        topicoActualizado.setAutor(topicoRequest.getAutor());
        topicoActualizado.setCurso(topicoRequest.getCurso());
        topicoActualizado.setStatus(topicoRequest.getStatus());

        try {
            Topico topicoGuardado = topicoService.actualizarTopico(id, topicoActualizado);
            return new ResponseEntity<>(topicoGuardado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTopico(@PathVariable Long id) {
        try {
            topicoService.borrarTopico(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
