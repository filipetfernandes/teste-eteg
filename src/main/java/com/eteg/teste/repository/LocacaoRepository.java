package com.eteg.teste.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eteg.teste.model.Filme;
import com.eteg.teste.model.Locacao;
import com.eteg.teste.model.Usuario;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

	@Query("SELECT loc FROM Locacao loc WHERE loc.usuario=(:usuario) and loc.dataDevolucao>=(:dataAtual)")
	List<Locacao> findByUsuario(@Param("usuario") Usuario usuario, @Param("dataAtual") Date dataAtual);
	
	@Query("SELECT loc FROM Locacao loc WHERE loc.filme=(:filme) and loc.dataDevolucao>=(:dataAtual)")
	List<Locacao> findByFilme(@Param("filme") Filme filme, @Param("dataAtual") Date dataAtual);
	
	@Query("SELECT loc FROM Locacao loc WHERE loc.dataDevolucao>=(:dataAtual)")
	List<Locacao> findLocacoesAtuais(@Param("dataAtual") Date dataAtual);

}
