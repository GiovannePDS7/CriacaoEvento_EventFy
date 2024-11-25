package com.evento.Cadastro.service;

import com.evento.Cadastro.dto.EventoDTO;
import com.evento.Cadastro.entity.Evento;
import com.evento.Cadastro.entity.Organizador;
import com.evento.Cadastro.repository.EventoRepository;
import com.evento.Cadastro.repository.OrganizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final OrganizadorRepository organizadorRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository, OrganizadorRepository organizadorRepository) {
        this.eventoRepository = eventoRepository;
        this.organizadorRepository = organizadorRepository;
    }

    // Obter todos os eventos
    public List<EventoDTO> listarTodos() {
        return eventoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Obter evento por ID
    public Optional<EventoDTO> buscarPorId(Long id) {
        return eventoRepository.findById(id).map(this::toDTO);
    }

    // Criar novo evento
    @Transactional
    public EventoDTO criarEvento(Long idOrganizador, EventoDTO eventoDTO) {
        Organizador organizador = organizadorRepository.findById(idOrganizador)
                .orElseThrow(() -> new IllegalArgumentException("Organizador não encontrado para o ID: " + idOrganizador));

        // Converte o DTO para a entidade
        Evento evento = toEntity(eventoDTO);

        // Associa o Organizador ao Evento
        evento.setOrganizador(organizador);

        // Salva o evento no banco de dados e retorna como DTO
        return toDTO(eventoRepository.save(evento));
    }

    // Atualizar evento existente
    public Optional<EventoDTO> atualizarEvento(Long id, EventoDTO eventoDTO) {
        return eventoRepository.findById(id).map(evento -> {
            evento.setNomeEvento(eventoDTO.getNomeEvento());
            evento.setDataEvento(eventoDTO.getDataEvento());
            evento.setHorarioInicio(eventoDTO.getHorarioInicio());
            evento.setHorarioFim(eventoDTO.getHorarioFim());
            evento.setLocalEvento(eventoDTO.getLocalEvento());
            evento.setTipoEvento(eventoDTO.getTipoEvento());
            evento.setIncluirTarefas(eventoDTO.isIncluirTarefas());
            evento.setListaConvidados(eventoDTO.isListaConvidados());
            evento.setFornecedores(eventoDTO.isFornecedores());

            return toDTO(eventoRepository.save(evento));
        });
    }

    // Remover evento por ID
    public boolean excluirEvento(Long id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Converter de Entidade para DTO
    private EventoDTO toDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();

        dto.setNomeEvento(evento.getNomeEvento());
        dto.setDataEvento(evento.getDataEvento());
        dto.setHorarioInicio(evento.getHorarioInicio());
        dto.setHorarioFim(evento.getHorarioFim());
        dto.setLocalEvento(evento.getLocalEvento());
        dto.setTipoEvento(evento.getTipoEvento());

        // Verificando o Organizador e extraindo seu ID
        dto.setIdOrganizador(Optional.ofNullable(evento.getOrganizador())
                .map(Organizador::getIdOrganizador)
                .orElse(null));

        // Mapeando campos booleanos
        dto.setIncluirTarefas(evento.isIncluirTarefas());
        dto.setListaConvidados(evento.isListaConvidados());
        dto.setFornecedores(evento.isFornecedores());

        return dto;
    }

    // Converter de DTO para Entidade
    private Evento toEntity(EventoDTO dto) {
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

        // Lógica para associar o Organizador ao Evento
        if (dto.getIdOrganizador() != null) {
            Organizador organizador = organizadorRepository.findById(dto.getIdOrganizador())
                    .orElseThrow(() -> new IllegalArgumentException("Organizador não encontrado"));
            evento.setOrganizador(organizador);
        }

        return evento;
    }
}
