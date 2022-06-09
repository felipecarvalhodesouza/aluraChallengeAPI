package br.com.alura.apirest.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.alura.apirest.repository.ReceitaRepository;

@Entity
public class Receita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String descricao;
	private BigDecimal valor;
	private LocalDate data;
	
	public Receita() {
		
	}
	
	public Receita(String descricao,BigDecimal valor, LocalDate data) {
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public boolean isDuplicada(ReceitaRepository receitaRepository) {
		List<Receita> receitaList = receitaRepository.findByDescricao(descricao);
		if(!receitaList.isEmpty()) {
			return receitaList.stream().filter(this::isMesmoMes).findAny().isPresent();
		}
		return false;
	}

	private boolean isMesmoMes(Receita receita) {
		if(this.id == receita.getId()) {
			return false;
		}
		
		if(this.data.getYear() != (receita.getData().getYear())) {
			return false;
		}

		return this.data.getMonth().getValue() == receita.getData().getMonth().getValue();
	}

}
