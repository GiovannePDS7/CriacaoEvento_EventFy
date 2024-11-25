package com.evento.Cadastro.repository;

import com.evento.Cadastro.entity.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {

}
