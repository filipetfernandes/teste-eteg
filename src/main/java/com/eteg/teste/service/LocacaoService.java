package com.eteg.teste.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eteg.teste.model.Filme;
import com.eteg.teste.model.Locacao;
import com.eteg.teste.model.Usuario;
import com.eteg.teste.repository.LocacaoRepository;
import com.eteg.teste.util.Validate;

@Service
public class LocacaoService {

	@Autowired
	private LocacaoRepository repository;

	public List<Locacao> findAll() {
		return repository.findAll();
	}

	public Locacao findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Locacao save(Locacao locacao) {
		return repository.save(locacao);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public int getQuantidadeFilmesAlugados(Usuario usuario) {
		return repository.findByUsuario(usuario, new Date()).size();
	}
	
	public int getQuantidadeFilmesDisponiveis(Filme filme) {
		int quantidadeAlugados = repository.findByFilme(filme, new Date()).size();
		int quantidadeEstoque = filme.getQuantidade();
		return quantidadeEstoque - quantidadeAlugados;
	}

	public boolean regrasValidas(Locacao locacao) {
		if (!Validate.dataEscolhidaMaiorQueAtual(locacao.getDataDevolucao())) {
			locacao.setMensagemAoSalvar("Escolha uma data de devolução a partir de amanhã.");
			return false;
		}
		if (getQuantidadeFilmesDisponiveis(locacao.getFilme()) == 0) {
			locacao.setMensagemAoSalvar("Todos exemplares deste filme estão alugados.");
			return false;
		}
		if (getQuantidadeFilmesAlugados(locacao.getUsuario()) >= 5) {
			locacao.setMensagemAoSalvar("O usuário já possui atualmente 5 filmes alugados.");
			return false;
		}
		return true;
	}
	
	public boolean regrasValidasRenovacao(Locacao locacao) {
		if (locacao.getQuantidadeRenovacao() == 2) {
			locacao.setMensagemAoSalvar("Já foram feitas 2 renovações com essa locação.");
			return false;
		}
		return true;
	}
	
	public List<Locacao> findLocacoesAtuais(Date dataAtual) {
		return repository.findLocacoesAtuais(dataAtual);
	}

}
