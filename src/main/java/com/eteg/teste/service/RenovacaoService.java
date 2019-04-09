package com.eteg.teste.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eteg.teste.model.Renovacao;
import com.eteg.teste.repository.RenovacaoRepository;

@Service
public class RenovacaoService {

	@Autowired
	private RenovacaoRepository repository;

	public List<Renovacao> findAll() {
		return repository.findAll();
	}

	public Renovacao findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Renovacao save(Renovacao renovacao) {
		return repository.save(renovacao);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

}
