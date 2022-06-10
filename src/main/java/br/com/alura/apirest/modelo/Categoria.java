package br.com.alura.apirest.modelo;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.alura.apirest.utils.Utils;

public enum Categoria {
	ALIMENTACAO(1, "Alimentação"),
	SAUDE(		2, "Saúde"),
	MORADIA(	3, "Moradia"),
	TRANSPORTE(	4, "Transporte"),
	EDUCACAO(	5, "Educação"),
	LAZER(		6, "Lazer"),
	IMPREVISTOS(7, "Imprevistos"),
	OUTROS(		8, "Outras");

	private Categoria(int codigoCategoria, String descricaoCategoria) {
		this.codigoCategoria = codigoCategoria;
		this.descricaoCategoria = descricaoCategoria;
	}

	int codigoCategoria;
	String descricaoCategoria;

	public int getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(int codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
	
	public static Categoria fromCodigo(int codigo) {
		for (Categoria categoria : Categoria.values()) {
			if(categoria.getCodigoCategoria() == codigo) {
				return categoria;
			}
		}
		return OUTROS;
	}
	
	@JsonCreator
	public static Categoria getCategoria(String categoria) {
	 
	    for (Categoria cat : Categoria.values()) {
	        if(Utils.equalsIgnoreCase(cat.getDescricaoCategoria(), categoria)) {
	            return cat;
	        }
	    }

	    return OUTROS;
	}
}
