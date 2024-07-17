package com.alura.forohub.challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity(name = "Topico")
@Table(name = "topicos")
//@Data is like having implicit @Getter, @Setter, @ToString, @EqualsAndHashCode and
// @RequiredArgsConstructor annotations on the class (except that no constructor will
// be generated if any explicitly written constructors already exist).
@Data

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
