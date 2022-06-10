package br.com.alura.apirest.modelo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Duplicavel {

	LocalDate getData();
	long getId();
	boolean isDuplicada(JpaRepository<?, Long> repository);

	default boolean isMesmoMes(Movimentacao movimentacao) {
		if (this.getId() == movimentacao.getId()) {
			return false;
		}

		if (this.getData().getYear() != (movimentacao.getData().getYear())) {
			return false;
		}

		return this.getData().getMonth().getValue() == movimentacao.getData().getMonth().getValue();
	}

}
