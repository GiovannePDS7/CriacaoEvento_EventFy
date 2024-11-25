package com.evento.Cadastro.controller;

import com.evento.Cadastro.dto.EventoDTO;
import com.evento.Cadastro.entity.Evento;
import com.evento.Cadastro.entity.Organizador;
import com.evento.Cadastro.repository.EventoRepository;
import com.evento.Cadastro.repository.OrganizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private OrganizadorRepository organizadorRepository;

    // Usando @RequestParam para obter o idOrganizador na query string
    @PostMapping("/criarEvento")
    public ResponseEntity<EventoDTO> criarEvento(@RequestParam Long idOrganizador, @RequestBody EventoDTO eventoDTO) {
        // Buscando o organizador pelo ID
        Organizador organizador = organizadorRepository.findById(idOrganizador)
                .orElseThrow(() -> new RuntimeException("Organizador não encontrado"));

        // Convertendo DTO para entidade Evento
        Evento evento = toEntity(eventoDTO, organizador);

        // Salvando a entidade Evento no banco de dados
        Evento eventoSalvo = eventoRepository.save(evento);

        // Convertendo de volta para DTO para retornar ao cliente
        EventoDTO eventoSalvoDTO = toDTO(eventoSalvo);

        // Retorna a resposta com o evento salvo e código de sucesso HTTP
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoSalvoDTO);
    }

    // Método para converter EventoDTO para Evento (entidade)
    private Evento toEntity(EventoDTO dto, Organizador organizador) {
        Evento evento = new Evento();
        evento.setNomeEvento(dto.getNomeEvento());
        evento.setDataEvento(dto.getDataEvento());
        evento.setHorarioInicio(dto.getHorarioInicio());
        evento.setHorarioFim(dto.getHorarioFim());
        evento.setLocalEvento(dto.getLocalEvento());
        evento.setTipoEvento(dto.getTipoEvento());
        evento.setIncluirTarefas(dto.isIncluirTarefas());
        evento.setListaConvidados(dto.isListaConvidados());
        evento.setFornecedores(dto.isFornecedores());

        // Associa o organizador ao evento
        evento.setOrganizador(organizador);

        return evento;
    }

    // Método para converter a entidade Evento para EventoDTO
    private EventoDTO toDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setNomeEvento(evento.getNomeEvento());
        dto.setDataEvento(evento.getDataEvento());
        dto.setHorarioInicio(evento.getHorarioInicio());
        dto.setHorarioFim(evento.getHorarioFim());
        dto.setLocalEvento(evento.getLocalEvento());
        dto.setTipoEvento(evento.getTipoEvento());
        dto.setIdOrganizador(evento.getOrganizador() != null ? evento.getOrganizador().getIdOrganizador() : null);
        dto.setIncluirTarefas(evento.isIncluirTarefas());
        dto.setListaConvidados(evento.isListaConvidados());
        dto.setFornecedores(evento.isFornecedores());
        return dto;
    }
}
