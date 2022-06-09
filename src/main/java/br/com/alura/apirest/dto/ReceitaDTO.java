package br.com.alura.apirest.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.apirest.modelo.Receita;
import br.com.alura.apirest.utils.Utils;

public class ReceitaDTO {

	private String descricao;
	private BigDecimal valor;
	private String data;

	public ReceitaDTO(Receita receita) {
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
		this.data = Utils.toString(receita.getData());
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

	public static List<ReceitaDTO> converter(List<Receita> receitas) {
		return receitas.stream().map(ReceitaDTO::new).collect(Collectors.toList());
	}

}
