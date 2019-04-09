package com.eteg.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eteg.teste.model.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

}
