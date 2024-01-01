package com.IOT.IOT_system.DTO;

public class Login_response {
    private String message;
    private Long id;
    private String Role;

    public Login_response(String message, Long id,String Role) {
        this.message = message;
        this.id = id;
        this.Role = Role;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
