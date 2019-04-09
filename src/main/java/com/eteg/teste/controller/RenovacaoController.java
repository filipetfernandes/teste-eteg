package com.eteg.teste.controller;

import java.util.Calendar;
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
import com.eteg.teste.model.Renovacao;
import com.eteg.teste.model.Usuario;
import com.eteg.teste.service.LocacaoService;
import com.eteg.teste.service.RenovacaoService;

@Controller
@RequestMapping("/locacao")
public class RenovacaoController {

	@Autowired
	private LocacaoService locacaoService;

	@Autowired
	private RenovacaoService renovacaoService;

	private Locacao locacao;
	private Locacao locacaoSelecionada;
	private List<Locacao> locacoes;

	@GetMapping("/renovar")
	public ModelAndView renovar(Locacao locacao, String mensagem, boolean sucesso) {
		ModelAndView mv = new ModelAndView("locacao/renovarLocacao");
		if (locacaoSelecionada == null || locacaoSelecionada.getId() == null) {
			locacaoSelecionada = new Locacao();
			locacaoSelecionada.setUsuario(new Usuario());
			locacaoSelecionada.setFilme(new Filme());
		}
		this.locacao = locacao;
		this.locacoes = locacaoService.findLocacoesAtuais(new Date());
		mv.addObject("listaLocacoes", locacoes);
		mv.addObject("locacaoSelecionada", locacaoSelecionada);
		mv.addObject("mensagem", mensagem);
		mv.addObject("sucesso", sucesso);
		return mv;
	}

	@GetMapping("/escolherLocacao/{id}")
	public ModelAndView escolherLocacao(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("locacao/renovarLocacao");
		locacaoSelecionada = locacaoService.findById(id);
		mv.addObject("listaLocacoes", locacoes);
		mv.addObject("locacaoSelecionada", locacaoSelecionada);
		mv.addObject("locacao", locacao);
		return mv;
	}

	@PostMapping("/salvarRenovacao")
	public ModelAndView salvarRenovacao(@Valid Locacao locacao, BindingResult result) {
		if (locacaoSelecionada == null || locacaoSelecionada.getId() == null) {
			return renovar(locacao, "Escolha uma locação para ser feita a renovação.", false);
		}
		if (result.hasErrors()) {
			return renovar(locacao, "Preencha todos os campos.", false);
		}
		if (locacaoService.regrasValidasRenovacao(locacaoSelecionada)) {
			Date dataAntiga = locacaoSelecionada.getDataDevolucao();
			locacaoSelecionada.setQuantidadeRenovacao(locacaoSelecionada.getQuantidadeRenovacao() + 1);
			adicionarDiasDataDevolucao(locacao.getQuantidadeDiasRenovacao());
			locacaoService.save(locacaoSelecionada);
			salvarRenovacao(locacaoSelecionada, locacao.getQuantidadeDiasRenovacao(), dataAntiga);
			locacaoSelecionada = null;
			locacao.setQuantidadeDiasRenovacao(null);
			return renovar(new Locacao(), "Locação renovada com sucesso!", true);
		} else {
			return renovar(locacao, locacaoSelecionada.getMensagemAoSalvar(), false);
		}
	}

	private void salvarRenovacao(Locacao locacaoSelecionada, Integer diasRenovados, Date dataAntiga) {
		Renovacao renovacao = new Renovacao();
		renovacao.setLocacao(locacaoSelecionada);
		renovacao.setDataRenovacao(new Date());
		renovacao.setDataDevolucaoNova(locacaoSelecionada.getDataDevolucao());
		renovacao.setDataDevolucaoAntiga(dataAntiga);
		renovacaoService.save(renovacao);
	}

	private void adicionarDiasDataDevolucao(Integer dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(locacaoSelecionada.getDataDevolucao());
		calendar.add(Calendar.DATE, dias);
		locacaoSelecionada.setDataDevolucao(calendar.getTime());
	}

}
