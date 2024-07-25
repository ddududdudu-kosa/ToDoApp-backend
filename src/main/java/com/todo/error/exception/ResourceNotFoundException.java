package com.todo.error.exception;

/**
 * packageName    : com.todo.error.exception
 * fileName       : ResourceNotFoundException
 * author         : leejongseop
 * date           : 2024-07-23
 * description    : Reference Yeong-Huns
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        leejongseop       최초 생성
 */
public class ResourceNotFoundException extends CustomBaseException {
    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND);
    }
}
