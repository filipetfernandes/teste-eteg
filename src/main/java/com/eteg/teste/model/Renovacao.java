package com.eteg.teste.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "renovacao")
public class Renovacao extends Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Locacao locacao;

	@Column
	private Date dataRenovacao;

	@Column
	private Date dataDevolucaoAntiga;

	@Column
	private Date dataDevolucaoNova;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	public Date getDataRenovacao() {
		return dataRenovacao;
	}

	public void setDataRenovacao(Date dataRenovacao) {
		this.dataRenovacao = dataRenovacao;
	}

	public Date getDataDevolucaoAntiga() {
		return dataDevolucaoAntiga;
	}

	public void setDataDevolucaoAntiga(Date dataDevolucaoAntiga) {
		this.dataDevolucaoAntiga = dataDevolucaoAntiga;
	}

	public Date getDataDevolucaoNova() {
		return dataDevolucaoNova;
	}

	public void setDataDevolucaoNova(Date dataDevolucaoNova) {
		this.dataDevolucaoNova = dataDevolucaoNova;
	}
	
	@Transient
	public String getDataRenovacaoFormatada() {
		if (dataRenovacao != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataRenovacao);
		}
		return "";
	}
	
	@Transient
	public String getDataDevolucaoAntigaFormatada() {
		if (dataDevolucaoAntiga != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataDevolucaoAntiga);
		}
		return "";
	}
	
	@Transient
	public String getDataDevolucaoNovaFormatada() {
		if (dataDevolucaoNova != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataDevolucaoNova);
		}
		return "";
	}

}
