package br.com.alura.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.apirest.modelo.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{
	
	List<Receita> findByDescricao(String descricao);
}
