package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;

import java.io.Serializable;

public class UserDto implements Serializable {

    private String username;
    private String password;
    private String email;
    private Long level;
    private String token;

    public UserDto() {
    }

    public UserDto(String username, String password, String email, Long level) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public void validate() throws DomainException {
        if (Validator.isNull(this.username))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.EMPTY_PARAMETER);

        if (Validator.isNull(this.password))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "رمز عبور"), ErrorMessage.EMPTY_PARAMETER);

    }
}
