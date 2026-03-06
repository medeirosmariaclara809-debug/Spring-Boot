package com.example.forum.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicoUpdateDTO(
        @NotBlank String titulo,
        @NotBlank String mensagem) {
}
