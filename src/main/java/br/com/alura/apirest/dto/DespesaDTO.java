package br.com.alura.apirest.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.apirest.modelo.Despesa;
import br.com.alura.apirest.utils.Utils;

public class DespesaDTO {

	private String descricao;
	private BigDecimal valor;
	private String data;

	public DespesaDTO(Despesa despesa) {
		this.descricao = despesa.getDescricao();
		this.valor = despesa.getValor();
		this.data = Utils.toString(despesa.getData());
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public static List<DespesaDTO> converter(List<Despesa> despesas) {
		return despesas.stream().map(DespesaDTO::new).collect(Collectors.toList());
	}

}
