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

import br.com.alura.apirest.controller.form.DespesaForm;
import br.com.alura.apirest.dto.DespesaDTO;
import br.com.alura.apirest.exception.DespesaDuplicadaNoMesException;
import br.com.alura.apirest.modelo.Despesa;
import br.com.alura.apirest.repository.DespesaRepository;

@Validated
@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private DespesaRepository despesaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<Despesa> inserir(@RequestBody @Valid DespesaForm form, UriComponentsBuilder uriBuilder) throws DespesaDuplicadaNoMesException {
		Despesa despesa = form.converter();
		
		if(despesa.isDuplicada(despesaRepository)) {
			throw new DespesaDuplicadaNoMesException("Já existe uma despesa com a mesma descrição para o mês selecionado.");
		}

		despesaRepository.save(despesa);

		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(despesa);
	}

	@GetMapping
	public List<DespesaDTO> buscarTodasAsDespesas(@RequestParam(required = false) String descricao) {
		if(descricao == null) {
			return DespesaDTO.converter(despesaRepository.findAll());
		}
		
		return DespesaDTO.converter(despesaRepository.findByDescricaoIgnoreCaseLike(descricao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DespesaDTO> buscarDespesa(@PathVariable Long id) {
		Optional<Despesa> despesa = despesaRepository.findById(id);
		if (despesa.isPresent()) {
			return ResponseEntity.ok(new DespesaDTO(despesa.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{ano}/{mes}")
	public List<DespesaDTO> buscarDespesasPorMesEAno(@PathVariable  @NotNull
																	@Min(message = "Ano não pode ser menor que 1970", value = 1970)
																	@Max(message = "Ano não pode ser maior que 2199", value = 2199) int ano,
													 @PathVariable  @NotNull
													 				@Min(message = "Mês não pode ser menor que 1", value = 1)
																	@Max(message = "Mês não pode ser maior que 12", value = 12) int mes) {
		LocalDate primeiroDiaDoMes = LocalDate.of(ano, mes, 1),
				  ultimoDiaDoMes   = primeiroDiaDoMes.with(TemporalAdjusters.lastDayOfMonth());
		
		return DespesaDTO.converter(despesaRepository.getTodasDespesasDeUmMes(primeiroDiaDoMes, ultimoDiaDoMes));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<DespesaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid DespesaForm form) throws DespesaDuplicadaNoMesException {
		Optional<Despesa> despesaOptional = despesaRepository.findById(id);
		if (despesaOptional.isPresent()) {
			
			Despesa despesaForm = form.converter();
			despesaForm.setId(id);
			
			if(despesaForm.isDuplicada(despesaRepository)) {
				throw new DespesaDuplicadaNoMesException("Já existe uma despesa com a mesma descrição para o mês selecionado.");
			}
			
			Despesa despesa = form.atualizar(despesaOptional.get());
			return ResponseEntity.ok(new DespesaDTO(despesa));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Despesa> optional = despesaRepository.findById(id);
		if (optional.isPresent()) {
			despesaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
