package com.eteg.teste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eteg.teste.model.Locacao;
import com.eteg.teste.model.Renovacao;
import com.eteg.teste.service.LocacaoService;
import com.eteg.teste.service.RenovacaoService;

@Controller
@RequestMapping("/locacao")
public class HistoricoController {

	@Autowired
	private LocacaoService locacaoService;
	
	@Autowired
	private RenovacaoService renovacaoService;

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("locacao/historicoLocacao");
		List<Locacao> historicoLocacoes = locacaoService.findAll();
		List<Renovacao> historicoRenovacoes = renovacaoService.findAll();
		mv.addObject("historico", historicoLocacoes);
		mv.addObject("historicoRenovacoes", historicoRenovacoes);
		return mv;
	}

}
