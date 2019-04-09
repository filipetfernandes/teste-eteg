package com.eteg.teste.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario extends Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@NotNull
	@NotEmpty
	private String nome;

	@Column
	@NotNull
	@NotEmpty
	private String sexo;

	@Column
	@NotNull
	@NotEmpty
	private String cpf;

	@Column
	@NotNull
	private Date dataNascimento;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
	private List<Locacao> locacoes;

	@Transient
	private String dataNascimentoString;

	@Transient
	private Boolean selecionado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Locacao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(List<Locacao> locacoes) {
		this.locacoes = locacoes;
	}

	public String getDataNascimentoString() {
		if (dataNascimento != null) {
			return new SimpleDateFormat("yyyy-MM-dd").format(dataNascimento);
		}
		return dataNascimentoString;
	}

	public void setDataNascimentoString(String dataNascimentoString) {
		if (dataNascimentoString != null && !dataNascimentoString.trim().equals("")) {
			try {
				dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse(dataNascimentoString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		this.dataNascimentoString = dataNascimentoString;
	}

	public Boolean getSelecionado() {
		if (selecionado == null) {
			return false;
		}
		return selecionado;
	}

	public void setSelecionado(Boolean selecionado) {
		if (selecionado == null) {
			this.selecionado = false;
		}
		this.selecionado = selecionado;
	}

	@Transient
	public String getDataNascimentoFormatada() {
		if (dataNascimento != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataNascimento);
		}
		return "";
	}

	@Transient
	public String getSexoFormatado() {
		if (sexo != null) {
			if (sexo.trim().toLowerCase().equals("m")) {
				return "Masculino";
			}
			return "Feminino";
		}
		return "";
	}
	
	@Transient
	public String getCpfSemPontuacao() {
		if (cpf != null) {
			return cpf.replace(".", "").replace("-", "");
		}
		return "";
	}
	
}
