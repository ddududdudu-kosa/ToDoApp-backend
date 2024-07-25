package com.todo.error.exception;

/**
 * packageName    : com.todo.error.exception
 * fileName       : BadRequestException
 * author         : leejongseop
 * date           : 2024-07-23
 * description    : Reference Yeong-Huns
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        leejongseop       최초 생성
 */
public class BadRequestException extends CustomBaseException {
    public BadRequestException(ErrorCode errorCode){
        super(errorCode.getMessage(), errorCode);
    }
    public BadRequestException(){
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}