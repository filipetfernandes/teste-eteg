package com.eteg.teste.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eteg.teste.model.Usuario;
import com.eteg.teste.repository.UsuarioRepository;
import com.eteg.teste.util.Validate;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public List<Usuario> findAll() {
		return repository.findAll();
	}

	public Usuario findById(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario) {
		return repository.save(usuario);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public boolean regrasValidas(Usuario usuario) {
		if (usuario.getCpf() != null && !Validate.cpfValido(usuario.getCpfSemPontuacao())) {
			usuario.setMensagemAoSalvar("O CPF digitado está inválido.");
			return false;
		}
		if (usuario.getDataNascimento() != null && !Validate.maiorDezoitoAnos(usuario.getDataNascimento())) {
			usuario.setMensagemAoSalvar("O usuário precisa ser maior de 18 anos para realizar o cadastro.");
			return false;
		}
		return true;
	}

}
