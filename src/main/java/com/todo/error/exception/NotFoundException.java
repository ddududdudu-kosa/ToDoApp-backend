package com.todo.error.exception;

/**
 * packageName    : com.todo.error.exception
 * fileName       : NotFoundException
 * author         : leejongseop
 * date           : 2024-07-23
 * description    : Reference Yeong-Huns
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        leejongseop       최초 생성
 */
public class NotFoundException extends CustomBaseException{
    public NotFoundException(ErrorCode errorCode){
        super(errorCode.getMessage(), errorCode);
    }
    public NotFoundException(){
        super(ErrorCode.NOT_FOUND);
    }
}
