package br.com.alura.apirest.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.apirest.controller.form.ReceitaForm;
import br.com.alura.apirest.dto.ReceitaDTO;
import br.com.alura.apirest.exception.ReceitaDuplicadaNoMesException;
import br.com.alura.apirest.modelo.Receita;
import br.com.alura.apirest.repository.ReceitaRepository;

@RestController
@RequestMapping("/receitas")
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
	public List<ReceitaDTO> buscarTodasAsReceitas() {
			List<Receita> receitas = receitaRepository.findAll();
			return ReceitaDTO.converter(receitas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDTO> buscarReceita(@PathVariable Long id) {
		Optional<Receita> receita = receitaRepository.findById(id);
		if (receita.isPresent()) {
			return ResponseEntity.ok(new ReceitaDTO(receita.get()));
		}

		return ResponseEntity.notFound().build();
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
}
