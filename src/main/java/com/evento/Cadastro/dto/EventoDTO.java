package com.evento.Cadastro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventoDTO {

    private Long idEvento;
    private String nomeEvento;
    private LocalDate dataEvento;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String localEvento;
    private String tipoEvento;
    private Long idOrganizador;
    private boolean incluirTarefas;
    private boolean listaConvidados;
    private boolean fornecedores;


}
