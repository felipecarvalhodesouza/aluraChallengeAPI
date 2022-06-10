package br.com.alura.apirest.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.alura.apirest.modelo.Categoria;
import br.com.alura.apirest.modelo.Despesa;
import br.com.alura.apirest.utils.Utils;

public class DespesaForm {

	@NotNull(message = "Descrição é obrigatória")
	@NotEmpty(message = "Descrição é obrigatória")
	private String descricao;

	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(message = "Valor inválido", value = "0.01")
	private BigDecimal valor;

	@NotNull(message = "Data é obrigatória")
	@NotEmpty(message = "Data é obrigatória")
	@Pattern(message = "Data inválida", regexp = "^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
	private String data;
	
	private String categoria;

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
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Despesa converter() {
		return new Despesa(descricao, valor, Utils.fromString(data), Categoria.getCategoria(categoria));
	}
	
	public Despesa atualizar(Despesa despesaBanco) {
		
		despesaBanco.setDescricao(this.descricao);
		despesaBanco.setValor(this.valor);
		despesaBanco.setData(Utils.fromString(data));
		
		if(this.categoria != null && !this.categoria.trim().isEmpty()) {
			despesaBanco.setCategoria(Categoria.getCategoria(this.categoria));
		}
		
		return despesaBanco;
	}
}
