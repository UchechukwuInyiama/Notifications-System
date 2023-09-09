package com.messagingappdemo.demo.exception;

import com.messagingappdemo.demo.dto.ApiResponse;
import com.messagingappdemo.demo.dto.ResponseMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private final String LOGGER_STRING_GET="url ->{} response -> {}";

    private final String LOGGER_STRING_POST="url ->{} request -> {} response ->{}";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String>errors=ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x ->x.getDefaultMessage())
                .collect(Collectors.toList());
        ApiResponse res= ApiResponse.getFailedResponse(ResponseMessages.FAILED.getCode(),ResponseMessages.FAILED.getMessage() +", "+errors.toString());
        log.error(LOGGER_STRING_GET, null, errors.toString());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ClassNotFoundException.class)
   public ResponseEntity<?> handleHttpClientErrorNotFoundException(ClassNotFoundException ex, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
        ApiResponse response=ApiResponse.getFailedResponse(ResponseMessages.FAILED.getCode(),ResponseMessages.FAILED.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(),ResponseMessages.FAILED.toString());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = HttpClientErrorException.MethodNotAllowed.class)
    public ResponseEntity<?> handleHttpClientMethodNotFoundException(HttpClientErrorException.MethodNotAllowed ex, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
        ApiResponse response=ApiResponse.getFailedResponse(ResponseMessages.FAILED.getCode(),ResponseMessages.FAILED.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(),ResponseMessages.NOT_ALLOWED.toString());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleHttpClientInternalServerErrorException(RuntimeException ex, HttpServletRequest request) {
        ApiResponse response=ApiResponse.getErrorResponse(ResponseMessages.INTERNAL_SERVER_ERROR.getCode(),ex.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(),ResponseMessages.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceNotFoundException(RuntimeException ex, HttpServletRequest request) {
        ApiResponse response=ApiResponse.getInvalidResponse(ResponseMessages.INVALID.getCode(),ex.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(),ResponseMessages.NOT_FOUND.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(value = InvalidResourceException.class)
    public ResponseEntity<?> invalidResourceException(InvalidResourceException ex, HttpServletRequest request) {
        ApiResponse response=ApiResponse.getInvalidResponse(ResponseMessages.INVALID.getCode(),ex.getMessage());
        log.error(LOGGER_STRING_GET,request.getRequestURL(),ResponseMessages.INVALID.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
