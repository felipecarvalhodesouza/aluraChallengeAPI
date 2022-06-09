package br.com.alura.apirest.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.apirest.controller.form.ReceitaForm;
import br.com.alura.apirest.modelo.Receita;
import br.com.alura.apirest.repository.ReceitaRepository;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

	@Autowired
	private ReceitaRepository receitaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<Receita> inserir(@RequestBody @Valid ReceitaForm form, UriComponentsBuilder uriBuilder) {
		Receita receita = form.converter();

		receitaRepository.save(receita);

		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(receita);
	}
}
