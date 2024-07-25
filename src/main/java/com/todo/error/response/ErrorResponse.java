package com.todo.error.response;

import com.todo.error.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * packageName    : com.todo.error.response
 * fileName       : ErrorResponse
 * author         : leejongseop
 * date           : 2024-07-23
 * description    : Reference Yeong-Huns
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        leejongseop       최초 생성
 */
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String Message;
    private String code;

    public ErrorResponse(final ErrorCode code) {
        this.Message = code.getMessage();
        this.code = code.getCode();
    }

    public ErrorResponse(final ErrorCode code, final String message) {
        this.Message = message;
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String message) {
        return new ErrorResponse(code, message);
    }
}
