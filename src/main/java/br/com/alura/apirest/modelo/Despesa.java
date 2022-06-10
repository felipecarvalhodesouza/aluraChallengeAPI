package br.com.alura.apirest.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.apirest.repository.DespesaRepository;

@Entity
public class Despesa extends Movimentacao implements Duplicavel{
	
	public Despesa() {
		super();
	}
	
	public Despesa(String descricao,BigDecimal valor, LocalDate data) {
		super(descricao, valor, data);
	}

	@Override
	public boolean isDuplicada(JpaRepository<?, Long> repository) {
		DespesaRepository despesaRepository = (DespesaRepository) repository;
		List<Despesa> despresaList = despesaRepository.findByDescricao(getDescricao());
		if (!despresaList.isEmpty()) {
			return despresaList.stream().filter(this::isMesmoMes).findAny().isPresent();
		}
		return false;
	}
}
