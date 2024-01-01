package com.IOT.IOT_system.DTO;

import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
public class LoginDTO {
    private String user_tk;
    private String user_mk;

    public LoginDTO() {
    }

    public LoginDTO(String user_tk, String user_mk) {
        this.user_tk = user_tk;
        this.user_tk = user_mk;
    }

    public void setUser_tk(String user_tk) {
        this.user_tk = user_tk;
    }

    public void setUser_mk(String user_mk) {
        this.user_mk = user_mk;
    }
}