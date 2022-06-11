package br.com.alura.apirest.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.apirest.dto.ResumoDTO;
import br.com.alura.apirest.repository.DespesaRepository;
import br.com.alura.apirest.repository.ReceitaRepository;

@RestController
@RequestMapping("/resumo")
@Validated
public class ResumoController {

	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;

	@GetMapping("/{ano}/{mes}")
	public ResumoDTO buscarResumoMes(@PathVariable  @NotNull
																	@Min(message = "Ano não pode ser menor que 1970", value = 1970)
																	@Max(message = "Ano não pode ser maior que 2199", value = 2199) int ano,
													 @PathVariable  @NotNull
													 				@Min(message = "Mês não pode ser menor que 1", value = 1)
																	@Max(message = "Mês não pode ser maior que 12", value = 12) int mes) {
		LocalDate primeiroDiaDoMes = LocalDate.of(ano, mes, 1),
				  ultimoDiaDoMes   = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());
		return new ResumoDTO(receitaRepository.getTodasReceitasDeUmMes(primeiroDiaDoMes, ultimoDiaDoMes), despesaRepository.getTodasDespesasDeUmMes(primeiroDiaDoMes, ultimoDiaDoMes));
	}
}
