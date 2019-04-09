package com.eteg.teste.controller;

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
import com.eteg.teste.service.FilmeService;

@Controller
@RequestMapping("/filme")
public class FilmeController {

	@Autowired
	private FilmeService service;

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("filme/listFilme");
		List<Filme> filmes = service.findAll();
		mv.addObject("listaFilmes", filmes);
		return mv;
	}

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Filme filme, String mensagem, boolean sucesso) {
		ModelAndView mv = new ModelAndView("filme/formFilme");
		mv.addObject("filme", filme);
		mv.addObject("mensagem", mensagem);
		mv.addObject("sucesso", sucesso);
		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("filme/formFilme");
		Filme filmeEditado = service.findById(id);
		mv.addObject("filme", filmeEditado);
		return mv;
	}

	@GetMapping("/excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id) {
		service.delete(id);
		return new ModelAndView("redirect:/filme/listar");
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Filme filme, BindingResult result) {
		if (result.hasErrors()) {
			return cadastrar(filme, "Preencha todos os campos.", false);
		}
		service.save(filme);
		return cadastrar(new Filme(), "Filme salvo com sucesso.", true);
	}

}
