package com.evento.Cadastro.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Organizador")
public class Organizador {

    @Id
    @Column(name = "Id_Organizador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrganizador;

    @Column(name = "Nome_Organizador", nullable = false)
    private String nomeOrganizador;

    @Lob
    @Column(name = "Foto_Organizador")
    private byte[] fotoOrganizador;

    @Column(name = "Email_Organizador", unique = true, nullable = false)
    private String emailOrganizador;

    @Column(name = "Senha_Organizador", nullable = false)
    private String senhaOrganizador;

    @Column(name = "Contato_Organizador")
    private String contatoOrganizador;

    @Column(name = "Data_Cadastro")
    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }
}