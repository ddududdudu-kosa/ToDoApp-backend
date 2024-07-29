package com.todo.config.mail;

import lombok.Data;

@Data
public class EmailRequest {
    private String email;
    private String code;
}
