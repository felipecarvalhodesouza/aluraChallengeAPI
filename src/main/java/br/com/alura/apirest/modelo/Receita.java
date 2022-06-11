package br.com.alura.apirest.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.apirest.repository.ReceitaRepository;

@Entity
public class Receita extends Movimentacao implements Duplicavel{
	
	public Receita() {
		super();
	}
	
	public Receita(String descricao,BigDecimal valor, LocalDate data) {
		super(descricao, valor, data);
	}

	@Override
	public boolean isDuplicada(JpaRepository<?, Long> repository) {
		ReceitaRepository receitaRepository = (ReceitaRepository) repository;
		List<Receita> receitaList = receitaRepository.findByDescricaoIgnoreCase(getDescricao());
		if (!receitaList.isEmpty()) {
			return receitaList.stream().filter(this::isMesmoMes).findAny().isPresent();
		}
		return false;
	}
	

}
