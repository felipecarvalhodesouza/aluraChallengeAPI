package br.com.alura.apirest.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}
