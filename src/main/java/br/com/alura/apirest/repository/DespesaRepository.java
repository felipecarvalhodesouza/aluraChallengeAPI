package br.com.alura.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.apirest.modelo.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
	
	List<Despesa> findByDescricao(String descricao);
}
