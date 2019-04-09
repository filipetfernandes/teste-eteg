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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "locacao")
public class Locacao extends Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Filme filme;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Usuario usuario;

	@Column
	private Date dataLocacao;

	@Column
	private Date dataDevolucao;

	@Column
	private Integer quantidadeRenovacao;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "locacao")
	private List<Renovacao> renovacoes;

	@Transient
	private String dataDevolucaoString;

	@Transient
	private Integer quantidadeDiasRenovacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(Date dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Integer getQuantidadeRenovacao() {
		return quantidadeRenovacao;
	}

	public void setQuantidadeRenovacao(Integer quantidadeRenovacao) {
		this.quantidadeRenovacao = quantidadeRenovacao;
	}

	public String getDataDevolucaoString() {
		if (dataDevolucao != null) {
			return new SimpleDateFormat("yyyy-MM-dd").format(dataDevolucao);
		}
		return dataDevolucaoString;
	}

	public void setDataDevolucaoString(String dataDevolucaoString) {
		if (dataDevolucaoString != null && !dataDevolucaoString.trim().equals("")) {
			try {
				dataDevolucao = new SimpleDateFormat("yyyy-MM-dd").parse(dataDevolucaoString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		this.dataDevolucaoString = dataDevolucaoString;
	}

	@Transient
	public String getDataDevolucaoFormatada() {
		if (dataDevolucao != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataDevolucao);
		}
		return "";
	}
	
	@Transient
	public String getDataLocacaoFormatada() {
		if (dataLocacao != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataLocacao);
		}
		return "";
	}

	public Integer getQuantidadeDiasRenovacao() {
		return quantidadeDiasRenovacao;
	}

	public void setQuantidadeDiasRenovacao(Integer quantidadeDiasRenovacao) {
		this.quantidadeDiasRenovacao = quantidadeDiasRenovacao;
	}

}
