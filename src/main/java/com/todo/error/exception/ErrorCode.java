package com.todo.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * packageName    : com.todo.error.exception
 * fileName       : ErrorCode
 * author         : leejongseop
 * date           : 2024-07-23
 * description    : Reference Yeong-Huns
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        leejongseop       최초 생성
 */
@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E1", "페이지가 응답하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E2", "인가받지 않은 호출입니다."),
    NOT_VALID_JSON(HttpStatus.METHOD_NOT_ALLOWED, "E2", "JSON 형식을 기대했지만, JSON 형식으로 변환할 수 없는 값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E3", "서버 에러가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "E4", "존재하지 않는 대상입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E4", "존재하지 않는 유저입니다."),
    MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "E5", "지원하지 않는 형식의 입력값입니다."),
    ALREADY_EMAIL(HttpStatus.BAD_REQUEST, "E5", "이미 존재하는 이메일입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "E4", "찾을수 없습니다."),
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}
