package com.todo.error.exception;

import lombok.Getter;

/**
 * packageName    : com.todo.error.exception
 * fileName       : CustomBaseException
 * author         : leejongseop
 * date           : 2024-07-23
 * description    : Reference Yeong-Huns
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        leejongseop       최초 생성
 */
@Getter
public class CustomBaseException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomBaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}