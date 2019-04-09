package com.eteg.teste.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eteg.teste.model.Filme;
import com.eteg.teste.repository.FilmeRepository;

@Service
public class FilmeService {

	@Autowired
	private FilmeRepository repository;

	public List<Filme> findAll() {
		return repository.findAll();
	}

	public Filme findById(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public Filme save(Filme filme) {
		return repository.save(filme);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
