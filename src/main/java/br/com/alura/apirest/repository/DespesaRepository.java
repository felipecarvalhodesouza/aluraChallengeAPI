package br.com.alura.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.apirest.modelo.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
	
	@Query(nativeQuery = true,
			   value = "SELECT * FROM Despesa d WHERE TRANSLATE(lower(d.descricao),'ÀÁáàÉÈéèÍíÓóÒòÚúÃãÕõ','AAaaEEeeIiOoOoUuAaOo') like CONCAT('%', TRANSLATE(lower(:descricao), 'ÀÁáàÉÈéèÍíÓóÒòÚúÃãÕõ','AAaaEEeeIiOoOoUuAaOo'), '%') ")
		public List<Despesa> findByDescricaoIgnoreCaseLike(@Param("descricao") String descricao);
		
		@Query(nativeQuery = true,
				   value = "SELECT * FROM Despesa d WHERE TRANSLATE(lower(d.descricao),'ÀÁáàÉÈéèÍíÓóÒòÚúÃãÕõ','AAaaEEeeIiOoOoUuAaOo') = TRANSLATE(lower(:descricao), 'ÀÁáàÉÈéèÍíÓóÒòÚúÃãÕõ','AAaaEEeeIiOoOoUuAaOo') ")
			public List<Despesa> findByDescricaoIgnoreCase(@Param("descricao") String descricao);
}
