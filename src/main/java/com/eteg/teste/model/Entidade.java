package com.eteg.teste.model;

import java.io.Serializable;

import javax.persistence.Transient;

public class Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private String mensagemAoSalvar;

	public String getMensagemAoSalvar() {
		return mensagemAoSalvar;
	}

	public void setMensagemAoSalvar(String mensagemAoSalvar) {
		this.mensagemAoSalvar = mensagemAoSalvar;
	}

}
