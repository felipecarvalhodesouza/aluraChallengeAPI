package br.com.alura.apirest.config;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.alura.apirest.config.validation.ErrorDetail;
import br.com.alura.apirest.exception.ReceitaDuplicadaNoMesException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(ReceitaDuplicadaNoMesException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ReceitaDuplicadaNoMesException rdnme, HttpServletRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Erro na inserção de receita.");
        errorDetail.setDetail(rdnme.getMessage());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }
}
