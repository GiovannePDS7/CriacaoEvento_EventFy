package com.evento.Cadastro.dto;

import com.evento.Cadastro.entity.Organizador;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventoDTO {

    private String nomeEvento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEvento;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioInicio;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horarioFim;
    private String localEvento;
    private String tipoEvento;
    private Long idOrganizador;
    private boolean incluirTarefas;
    private boolean listaConvidados;
    private boolean fornecedores;
}
