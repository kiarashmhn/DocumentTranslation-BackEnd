package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;

import java.io.Serializable;

public class UserDto implements Serializable {

    private String username;
    private String password;
    private String token;

    public UserDto() {
    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
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

    public void validate() throws DomainException {
        if (Validator.isNull(this.username))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.EMPTY_PARAMETER);

        if (Validator.isNull(this.password))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "رمز عبور"), ErrorMessage.EMPTY_PARAMETER);

    }
}
