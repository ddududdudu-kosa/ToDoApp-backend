package com.todo.config.mail;

import lombok.Data;

@Data
public class EmailRequest {
    private String email;

    // Getter와 Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
