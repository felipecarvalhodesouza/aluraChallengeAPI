package br.com.alura.apirest.exception;

public abstract class MovimentacaoDuplicadaException extends Exception{
	
	protected static final long serialVersionUID = 1L;

	public MovimentacaoDuplicadaException(String message) {
		super(message);
	}

	public abstract String getTitle();

}
