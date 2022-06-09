package br.com.alura.apirest.exception;

public class ReceitaDuplicadaNoMesException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReceitaDuplicadaNoMesException(String message) {
		super(message);
	}

}
