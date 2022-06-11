package br.com.alura.apirest.config.validation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.Path.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.alura.apirest.exception.MovimentacaoDuplicadaException;

@RestControllerAdvice
public class ValidationHandler{

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDto> dto = new ArrayList<>();

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});

		return dto;
	}
	
	
    @ExceptionHandler(MovimentacaoDuplicadaException.class)
    public ResponseEntity<?> handleMovimentacaoDuplicadaException(MovimentacaoDuplicadaException mdnme, HttpServletRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle(mdnme.getTitle());
        errorDetail.setDetail(mdnme.getMessage());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }
	
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException mdnme, HttpServletRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Erro no tipo de parâmetro enviado");
        errorDetail.setDetail("Foi verificado que os tipos de parâmetros enviados não correspondem aos esperados pela API. Favor revisar a requisição");

        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }
    
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public List<ErroDeFormularioDto> handle(ConstraintViolationException exception) {
		List<ErroDeFormularioDto> dto = new ArrayList<>();
		
		dto.addAll(exception.getConstraintViolations().stream()
													  .map(this::getErroFromViolation)
													  .collect(Collectors.toList()));
		return dto;
	}
	
	private ErroDeFormularioDto getErroFromViolation(ConstraintViolation<?> violation) {
		Path propertyPath = violation.getPropertyPath();
		for (Node node : propertyPath) {
			if("mes".equalsIgnoreCase(node.getName()) || "ano".equalsIgnoreCase(node.getName())) {
				return new ErroDeFormularioDto(node.getName(), violation.getMessage());	
			}
		}
		return new ErroDeFormularioDto(propertyPath.toString(), violation.getMessage());
	}

}
