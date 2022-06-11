package br.com.alura.apirest.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.apirest.controller.form.ReceitaForm;
import br.com.alura.apirest.dto.ReceitaDTO;
import br.com.alura.apirest.exception.ReceitaDuplicadaNoMesException;
import br.com.alura.apirest.modelo.Receita;
import br.com.alura.apirest.repository.ReceitaRepository;

@RestController
@RequestMapping("/receitas")
@Validated
public class ReceitaController {

	@Autowired
	private ReceitaRepository receitaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<Receita> inserir(@RequestBody @Valid ReceitaForm form, UriComponentsBuilder uriBuilder) throws ReceitaDuplicadaNoMesException {
		Receita receita = form.converter();
		
		if(receita.isDuplicada(receitaRepository)) {
			throw new ReceitaDuplicadaNoMesException("Já existe uma receita com a mesma descrição para o mês selecionado.");
		}

		receitaRepository.save(receita);

		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(receita);
	}

	@GetMapping
	public List<ReceitaDTO> buscarTodasAsReceitas(@RequestParam(required = false) String descricao) {
		if(descricao == null) {
			return ReceitaDTO.converter(receitaRepository.findAll());
		}

		return ReceitaDTO.converter(receitaRepository.findByDescricaoIgnoreCaseLike(descricao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDTO> buscarReceita(@PathVariable Long id) {
		Optional<Receita> receita = receitaRepository.findById(id);
		if (receita.isPresent()) {
			return ResponseEntity.ok(new ReceitaDTO(receita.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{ano}/{mes}")
	public List<ReceitaDTO> buscarReceitasPorMesEAno(@PathVariable  @NotNull
																	@Min(message = "Ano não pode ser menor que 1970", value = 1970)
																	@Max(message = "Ano não pode ser maior que 2199", value = 2199) int ano,
													 @PathVariable  @NotNull
													 				@Min(message = "Mês não pode ser menor que 1", value = 1)
																	@Max(message = "Mês não pode ser maior que 12", value = 12) int mes) {
		LocalDate primeiroDiaDoMes = LocalDate.of(ano, mes, 1),
				  ultimoDiaDoMes   = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());
		
		return ReceitaDTO.converter(receitaRepository.getTodasReceitasDeUmMes(primeiroDiaDoMes, ultimoDiaDoMes));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ReceitaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ReceitaForm form) throws ReceitaDuplicadaNoMesException {
		Optional<Receita> receitaOptional = receitaRepository.findById(id);
		if (receitaOptional.isPresent()) {
			
			Receita receitaForm = form.converter();
			receitaForm.setId(id);
			
			if(receitaForm.isDuplicada(receitaRepository)) {
				throw new ReceitaDuplicadaNoMesException("Já existe uma receita com a mesma descrição para o mês selecionado.");
			}
			
			Receita receita = form.atualizar(receitaOptional.get());
			return ResponseEntity.ok(new ReceitaDTO(receita));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Receita> optional = receitaRepository.findById(id);
		if (optional.isPresent()) {
			receitaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
