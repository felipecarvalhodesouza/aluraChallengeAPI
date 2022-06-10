package br.com.alura.apirest.exception;

public class ReceitaDuplicadaNoMesException extends MovimentacaoDuplicadaException {

	private static final long serialVersionUID = 1L;

	public ReceitaDuplicadaNoMesException(String message) {
		super(message);
	}
	
	@Override
	public String getTitle() {
		return "Erro na validação de receita.";
	}

}
