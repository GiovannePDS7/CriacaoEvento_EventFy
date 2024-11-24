package com.evento.Cadastro.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Evento")
    private Long idEvento;

    @Column(name = "Nome_Evento", nullable = false, length = 100)
    private String nomeEvento;

    @Column(name = "Data_Evento", nullable = false)
    private LocalDate dataEvento;

    @Column(name = "Horario_Inicio", nullable = false)
    private LocalTime horarioInicio;

    @Column(name = "Horario_Fim", nullable = false)
    private LocalTime horarioFim;

    @Column(name = "Local_Evento", nullable = false, length = 150)
    private String localEvento;

    @Column(name = "Tipo_Evento", length = 50)
    private String tipoEvento;

    @ManyToOne
    @JoinColumn(name = "Id_Organizador", referencedColumnName = "Id_Organizador")
    private Organizador organizador;

    @Lob
    @Column(name = "Foto_Evento")
    private byte[] fotoEvento;

    @Column(name = "Incluir_Tarefas", nullable = false)
    private boolean incluirTarefas = false;

    @Column(name = "Lista_Convidados", nullable = false)
    private boolean listaConvidados = false;

    @Column(name = "Fornecedores", nullable = false)
    private boolean fornecedores = false;
}