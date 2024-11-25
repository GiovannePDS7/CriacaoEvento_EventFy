package com.evento.Cadastro.dto;

import com.evento.Cadastro.entity.Organizador;
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
