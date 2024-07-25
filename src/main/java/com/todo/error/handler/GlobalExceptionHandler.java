package com.todo.error.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.todo.error.exception.CustomBaseException;
import com.todo.error.exception.ErrorCode;
import com.todo.error.exception.ResourceNotFoundException;
import com.todo.error.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * packageName    : com.todo.error.handler
 * fileName       : GlobalExceptionHandler
 * author         : leejongseop
 * date           : 2024-07-23
 * description    : Reference Yeong-Huns
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        leejongseop       최초 생성
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class  GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED , " [Detail log] : "+e.getMessage()));
        return createErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CustomBaseException.class)
    protected ResponseEntity<ErrorResponse> handle(CustomBaseException e){
        log.error("Response: {}", ErrorResponse.of(e.getErrorCode(),  " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException", e);
        return createErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e){
        log.error("HttpMessageNotReadableException", e);
        return createErrorResponse(ErrorCode.MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND , " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(ErrorCode.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ErrorResponse> handle(JsonProcessingException e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.NOT_VALID_JSON, " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(ErrorCode.NOT_VALID_JSON);
    }


    //최종 에러처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handle(Exception e){
        log.error("Response: {}", ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR , " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handle(AccessDeniedException e) {
        log.error("Response: {}", ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED, " 🥲[상세 메세지] : " + e.getMessage()));
        return createErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    }


    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode){
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode),
                errorCode.getStatus());
    }
}