package com.todo.config.mail;

import lombok.Data;
/**
* @packageName    : com.todo.config.mail
* @fileName        : EmailRequest.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
@Data
public class EmailRequest {
    private String email;
    private String code;
}
