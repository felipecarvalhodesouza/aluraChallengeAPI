package br.com.alura.apirest.exception;

public class DespesaDuplicadaNoMesException extends MovimentacaoDuplicadaException{

	private static final long serialVersionUID = 1L;

	public DespesaDuplicadaNoMesException(String message) {
		super(message);
	}

	@Override
	public String getTitle() {
		return "Erro na validação de despesa.";
	}

}
