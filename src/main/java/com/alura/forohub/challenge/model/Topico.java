package com.alura.forohub.challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensaje;

    @NotBlank
    private String autor;

    @NotBlank
    private String curso;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @NotBlank
    private String status;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters and Setters
}
