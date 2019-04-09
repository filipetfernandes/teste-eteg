package com.eteg.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eteg.teste.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
