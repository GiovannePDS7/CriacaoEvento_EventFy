package com.evento.Cadastro.controller;

import com.evento.Cadastro.dto.EventoDTO;
import com.evento.Cadastro.entity.Organizador;
import com.evento.Cadastro.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // Listar todos os eventos
    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarTodos() {
        List<EventoDTO> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }

    // Buscar evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Long id) {
        Optional<EventoDTO> evento = eventoService.buscarPorId(id);
        return evento.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo evento
    @PostMapping("/criarEvento")
    public ResponseEntity<EventoDTO> criarEvento(
            @RequestParam("idOrganizador") Long idOrganizador,
            @RequestBody EventoDTO eventoDTO) {
        EventoDTO novoEvento = eventoService.criarEvento(idOrganizador, eventoDTO);
        return ResponseEntity.ok(novoEvento);
    }


    // Atualizar um evento existente
    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizarEvento(
            @PathVariable Long id,
            @RequestBody EventoDTO eventoDTO) {
        Optional<EventoDTO> eventoAtualizado = eventoService.atualizarEvento(id, eventoDTO);
        return eventoAtualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar um evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEvento(@PathVariable Long id) {
        if (eventoService.excluirEvento(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
