package com.evento.Cadastro.service;

import com.evento.Cadastro.dto.EventoDTO;
import com.evento.Cadastro.entity.Evento;
import com.evento.Cadastro.repository.EventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private EventoRepository eventoRepository;  // Repositório de Evento

    @Autowired
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // Obter todos os eventos
    public List<EventoDTO> listarTodos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Obter evento por ID
    public Optional<EventoDTO> buscarPorId(Long id) {
        return eventoRepository.findById(id).map(this::toDTO);
    }

    // Criar novo evento
    @Transactional
    public EventoDTO criarEvento(EventoDTO eventoDTO) {
        Evento evento = toEntity(eventoDTO);
        Evento eventoSalvo = eventoRepository.save(evento);
        return toDTO(eventoSalvo);
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
            // Atualize o organizador apenas se necessário
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
        dto.setIdEvento(evento.getIdEvento());
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
        // Adicione lógica para buscar o organizador se necessário
        return evento;
    }
}
