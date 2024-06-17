package com.dictionaryapp.model.dto;

import com.dictionaryapp.validation.annotation.UniqueEmail;
import com.dictionaryapp.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDto {



    @Size(min=3, max= 20)
    @NotBlank(message = "Username cannot be empty!")
    private String username;


    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Enter valid email!")
    private String email;

    @NotBlank
    @Size(min=3, max= 20)
    private String password;

    @Size(min=3, max= 20)
    private String confirmPassword;

    public UserRegisterDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
