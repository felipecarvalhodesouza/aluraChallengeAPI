package br.com.alura.apirest.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.apirest.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);

}
