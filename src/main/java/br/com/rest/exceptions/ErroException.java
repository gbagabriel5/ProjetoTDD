package br.com.rest.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ErroException {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ResponseError> handle(MethodArgumentNotValidException exception){
        List<ResponseError> erros = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ResponseError erro = new ResponseError(LocalDateTime.now(),mensagem, e.getField());
            erros.add(erro);
        });
        return erros;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(BusinessException.class)
    public ResponseError handle(BusinessException exception){
        ResponseError response = new ResponseError(LocalDateTime.now(), exception.getMessage(),exception.getDetails());
        return response;
    }
}
