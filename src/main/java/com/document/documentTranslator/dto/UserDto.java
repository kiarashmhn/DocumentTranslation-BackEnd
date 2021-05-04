package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;


public class UserDto extends BaseDto {

    private String password;
    private String email;
    private Long level;
    private String token;
    private String phone;
    private Boolean enabled;

    public UserDto() {
    }

    public UserDto(String username, String password, String email, Long level, String phone) {
        this.setUsername(username);
        this.password = password;
        this.email = email;
        this.level = level;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void validate() throws DomainException {
        if (Validator.isNull(this.getUsername()))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.EMPTY_PARAMETER);

        if (Validator.isNull(this.password))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "رمز عبور"), ErrorMessage.EMPTY_PARAMETER);

    }

    public void registerValidate() throws DomainException {

        this.validate();
        if (Validator.isNull(this.email) && Validator.isNull(this.phone))
            throw new DomainException(ErrorMessage.EMPTY_EMAIL_PHONE);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
