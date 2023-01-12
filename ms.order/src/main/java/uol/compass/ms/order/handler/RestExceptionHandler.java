package uol.compass.ms.order.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import uol.compass.ms.order.exceptions.InvalidCepException;
import uol.compass.ms.order.exceptions.InvalidDateException;
import uol.compass.ms.order.model.constants.ErrorCode;
import uol.compass.ms.order.model.dto.response.ExceptionResponseDTO;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ErrorCode.INVALID_PARAMETER, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ErrorCode.INVALID_PARAMETER, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> errors = new ArrayList<>();
        fieldErrors.forEach(error -> 
            errors.add(String.format("%s : %s", error.getField(), error.getDefaultMessage()))
        );

        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ErrorCode.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ErrorCode.BAD_REQUEST, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ErrorCode.BAD_REQUEST, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ErrorCode.INTERNAL_SERVER_ERROR, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
    
    @ExceptionHandler(InvalidCepException.class)
    public final ResponseEntity<Object> handleInvalidCepException(InvalidCepException ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ErrorCode.INVALID_CEP, ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResponse);
    }

    @ExceptionHandler(InvalidDateException.class)
    public final ResponseEntity<Object> handleInvalidDateException(InvalidDateException ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ErrorCode.INVALID_DATE, ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResponse);
    }

}
