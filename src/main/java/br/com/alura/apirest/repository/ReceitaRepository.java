package br.com.alura.apirest.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.apirest.modelo.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
	
	@Query(nativeQuery = true,
		   value = "SELECT * FROM Receita r WHERE TRANSLATE(lower(r.descricao),'ÀÁáàÉÈéèÍíÓóÒòÚúÃãÕõ','AAaaEEeeIiOoOoUuAaOo') like CONCAT('%', TRANSLATE(lower(:descricao), 'ÀÁáàÉÈéèÍíÓóÒòÚúÃãÕõ','AAaaEEeeIiOoOoUuAaOo'), '%') ")
	public List<Receita> findByDescricaoIgnoreCaseLike(@Param("descricao") String descricao);
	
	@Query(nativeQuery = true,
			   value = "SELECT * FROM Receita r WHERE TRANSLATE(lower(r.descricao),'ÀÁáàÉÈéèÍíÓóÒòÚúÃãÕõ','AAaaEEeeIiOoOoUuAaOo') = TRANSLATE(lower(:descricao), 'ÀÁáàÉÈéèÍíÓóÒòÚúÃãÕõ','AAaaEEeeIiOoOoUuAaOo') ")
		public List<Receita> findByDescricaoIgnoreCase(@Param("descricao") String descricao);
	
	@Query(nativeQuery = true, value = "SELECT * FROM Receita r WHERE r.data BETWEEN :dataInicio AND :dataFim ")
		public List<Receita> getTodasReceitasDeUmMes(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
