package com.alura.forohub.challenge.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopicoRequest {

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensaje;

    @NotBlank
    private String autor;

    @NotBlank
    private String curso;

    @NotBlank
    private String status;
}
