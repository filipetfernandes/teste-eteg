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

import com.eteg.teste.model.Usuario;
import com.eteg.teste.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("usuario/listUsuario");
		List<Usuario> usuarios = service.findAll();
		mv.addObject("listaUsuarios", usuarios);
		return mv;
	}

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Usuario usuario, String mensagem, boolean sucesso) {
		ModelAndView mv = new ModelAndView("usuario/formUsuario");
		mv.addObject("usuario", usuario);
		mv.addObject("mensagem", mensagem);
		mv.addObject("sucesso", sucesso);
		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("usuario/formUsuario");
		Usuario usuarioEditado = service.findById(id);
		mv.addObject("usuario", usuarioEditado);
		return mv;
	}

	@GetMapping("/excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id) {
		service.delete(id);
		return new ModelAndView("redirect:/usuario/listar");
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			return cadastrar(usuario, "Preencha todos os campos.", false);
		}
		if (service.regrasValidas(usuario)) {
			service.save(usuario);
			usuario.setMensagemAoSalvar("Usuário salvo com sucesso!");
			return cadastrar(new Usuario(), usuario.getMensagemAoSalvar(), true);
		} else {
			return cadastrar(usuario, usuario.getMensagemAoSalvar(), false);
		}
	}

}
