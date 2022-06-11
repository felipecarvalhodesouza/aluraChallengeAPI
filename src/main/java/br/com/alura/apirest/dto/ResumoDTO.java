package br.com.alura.apirest.dto;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.alura.apirest.modelo.Categoria;
import br.com.alura.apirest.modelo.Despesa;
import br.com.alura.apirest.modelo.Receita;

public class ResumoDTO {

	private BigDecimal totalReceita;
	private BigDecimal totalDespesa;
	private BigDecimal saldo;
	private Map<String, BigDecimal> gastosPorCategoria = new LinkedHashMap<String, BigDecimal>();
	
	public ResumoDTO(List<Receita> receitas, List<Despesa> despesas) {
		this.totalReceita = receitas.stream().map(Receita::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.totalDespesa = despesas.stream().map(Despesa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.saldo = totalReceita.subtract(totalDespesa);
		for (Categoria categoria : Categoria.values()) {
			gastosPorCategoria.put(categoria.getDescricaoCategoria(), despesas.stream()
											 		  						  .filter(o -> categoria.equals(o.getCategoria()))
											 		  						  .map(Despesa::getValor)
											 		  						  .reduce(BigDecimal.ZERO, BigDecimal::add));
		}
	}

	public BigDecimal getTotalReceita() {
		return totalReceita;
	}

	public void setTotalReceita(BigDecimal totalReceita) {
		this.totalReceita = totalReceita;
	}

	public BigDecimal getTotalDespesa() {
		return totalDespesa;
	}

	public void setTotalDespesa(BigDecimal totalDespesa) {
		this.totalDespesa = totalDespesa;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Map<String, BigDecimal> getGastosPorCategoria() {
		return gastosPorCategoria;
	}

	public void setGastosPorCategoria(Map<String, BigDecimal> gastosPorCategoria) {
		this.gastosPorCategoria = gastosPorCategoria;
	}

}
