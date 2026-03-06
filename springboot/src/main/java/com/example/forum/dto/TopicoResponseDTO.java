package com.example.forum.dto;

import com.example.forum.model.EstadoTopico;
import com.example.forum.model.Topico;

import java.time.LocalDateTime;

public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        EstadoTopico estado,
        String autor,
        String curso) {
    public TopicoResponseDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getEstado(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome());
    }
}
