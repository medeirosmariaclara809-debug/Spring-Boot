package com.example.forum.repository;

import com.example.forum.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso AND YEAR(t.dataCriacao) = :ano")
    Page<Topico> findByCursoNomeAndAno(String nomeCurso, Integer ano, Pageable paginacao);

    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
}
