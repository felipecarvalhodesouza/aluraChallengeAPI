package br.com.alura.apirest.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.apirest.repository.DespesaRepository;

@Entity
public class Despesa extends Movimentacao implements Duplicavel{
	
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	public Despesa() {
		super();
	}
	
	public Despesa(String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {
		super(descricao, valor, data);
		this.categoria = categoria == null ? Categoria.OUTROS : Categoria.fromCodigo(categoria.getCodigoCategoria());
	}

	@Override
	public boolean isDuplicada(JpaRepository<?, Long> repository) {
		DespesaRepository despesaRepository = (DespesaRepository) repository;
		List<Despesa> despresaList = despesaRepository.findByDescricaoIgnoreCase(getDescricao());
		if (!despresaList.isEmpty()) {
			return despresaList.stream().filter(this::isMesmoMes).findAny().isPresent();
		}
		return false;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
