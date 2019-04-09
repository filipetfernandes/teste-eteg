package com.eteg.teste.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eteg.teste.model.Filme;
import com.eteg.teste.model.Locacao;
import com.eteg.teste.model.Usuario;
import com.eteg.teste.service.FilmeService;
import com.eteg.teste.service.LocacaoService;
import com.eteg.teste.service.UsuarioService;

@Controller
@RequestMapping("/locacao")
public class LocacaoController {

	@Autowired
	private LocacaoService locacaoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FilmeService filmeService;

	private List<Usuario> usuarios;
	private List<Filme> filmes;
	private Usuario usuarioSelecionado;
	private Filme filmeSelecionado;
	private Locacao locacao;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Locacao locacao, String mensagem, boolean sucesso) {
		ModelAndView mv = new ModelAndView("locacao/formLocacao");
		usuarios = usuarioService.findAll();
		filmes = filmeService.findAll();
		if (usuarioSelecionado == null || usuarioSelecionado.getId() == null) {
			usuarioSelecionado = new Usuario();
		}
		if (filmeSelecionado == null || filmeSelecionado.getId() == null) {
			filmeSelecionado = new Filme();
		}
		this.locacao = locacao;
		mv.addObject("listaUsuarios", usuarios);
		mv.addObject("listaFilmes", filmes);
		mv.addObject("usuarioSelecionado", usuarioSelecionado);
		mv.addObject("filmeSelecionado", filmeSelecionado);
		mv.addObject("locacao", locacao);
		mv.addObject("mensagem", mensagem);
		mv.addObject("sucesso", sucesso);
		return mv;
	}

	@GetMapping("/escolherUsuario/{id}")
	public ModelAndView escolherUsuario(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("locacao/formLocacao");
		usuarioSelecionado = usuarioService.findById(id);
		mv.addObject("listaUsuarios", usuarios);
		mv.addObject("listaFilmes", filmes);
		mv.addObject("usuarioSelecionado", usuarioSelecionado);
		mv.addObject("filmeSelecionado", filmeSelecionado);
		mv.addObject("locacao", locacao);
		return mv;
	}

	@GetMapping("/escolherFilme/{id}")
	public ModelAndView escolherFilme(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("locacao/formLocacao");
		filmeSelecionado = filmeService.findById(id);
		mv.addObject("listaUsuarios", usuarios);
		mv.addObject("listaFilmes", filmes);
		mv.addObject("usuarioSelecionado", usuarioSelecionado);
		mv.addObject("filmeSelecionado", filmeSelecionado);
		mv.addObject("locacao", locacao);
		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Locacao locacao, BindingResult result) {
		if (usuarioSelecionado != null && usuarioSelecionado.getId() != null) {
			locacao.setUsuario(usuarioSelecionado);
		} else {
			return cadastrar(locacao, "Escolha um usuário.", false);
		}
		if (filmeSelecionado != null && filmeSelecionado.getId() != null) {
			locacao.setFilme(filmeSelecionado);
		} else {
			return cadastrar(locacao, "Escolha um filme.", false);
		}
		if (result.hasErrors()) {
			return cadastrar(locacao, "Preencha todos os campos.", false);
		}
		if (locacaoService.regrasValidas(locacao)) {
			Usuario user = usuarioService.findById(usuarioSelecionado.getId());
			Filme movie = filmeService.findById(filmeSelecionado.getId());
			locacao.setDataLocacao(new Date());
			locacao.setQuantidadeRenovacao(0);
			locacao.setUsuario(user);
			locacao.setFilme(movie);
			locacaoService.save(locacao);
			usuarioSelecionado = null;
			filmeSelecionado = null;
			locacao.setMensagemAoSalvar("Locação realizada com sucesso!");
			return cadastrar(new Locacao(), locacao.getMensagemAoSalvar(), true);
		} else {
			return cadastrar(locacao, locacao.getMensagemAoSalvar(), false);
		}
	}

}
