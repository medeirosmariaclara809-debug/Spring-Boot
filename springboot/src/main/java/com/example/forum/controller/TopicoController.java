package com.example.forum.controller;

import com.example.forum.dto.TopicoRequestDTO;
import com.example.forum.dto.TopicoResponseDTO;
import com.example.forum.dto.TopicoUpdateDTO;
import com.example.forum.model.Topico;
import com.example.forum.repository.CursoRepository;
import com.example.forum.repository.TopicoRepository;
import com.example.forum.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid TopicoRequestDTO dados,
            UriComponentsBuilder uriBuilder) {
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity.badRequest().body("Tópico duplicado (mesmo título e mensagem).");
        }

        var autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        var curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        var topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoResponseDTO(topico));
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listar(
            @RequestParam(required = false) String nomeCurso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable paginacao) {

        Page<Topico> pagina;
        if (nomeCurso != null && ano != null) {
            pagina = topicoRepository.findByCursoNomeAndAno(nomeCurso, ano, paginacao);
        } else if (nomeCurso != null) {
            pagina = topicoRepository.findByCursoNome(nomeCurso, paginacao);
        } else {
            pagina = topicoRepository.findAll(paginacao);
        }

        var dtoPagina = pagina.map(TopicoResponseDTO::new);
        return ResponseEntity.ok(dtoPagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id) {
        var topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new TopicoResponseDTO(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoUpdateDTO dados) {
        var optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            var topico = optional.get();
            topico.setTitulo(dados.titulo());
            topico.setMensagem(dados.mensagem());
            // O Hibernate salva automaticamente ao final da transação devido ao estado
            // Managed/Persistent
            return ResponseEntity.ok(new TopicoResponseDTO(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        var topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
