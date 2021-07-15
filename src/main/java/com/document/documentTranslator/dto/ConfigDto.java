package com.document.documentTranslator.dto;

import com.document.documentTranslator.util.Validator;

import java.util.Map;

public class ConfigDto extends BaseDto {

    private String accName;
    private String accLastName;
    private String accNumber;
    private String address;
    private Map<String, Object> questions;
    private Long ribId;
    private String ribName;

    public boolean validate() {
        return Validator.notNull(accName) &&
                Validator.notNull(accLastName) &&
                Validator.notNull(accNumber) &&
                Validator.notNull(address) &&
                Validator.notNull(questions);
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccLastName() {
        return accLastName;
    }

    public void setAccLastName(String accLastName) {
        this.accLastName = accLastName;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Object> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, Object> questions) {
        this.questions = questions;
    }

    public Long getRibId() {
        return ribId;
    }

    public void setRibId(Long ribId) {
        this.ribId = ribId;
    }

    public String getRibName() {
        return ribName;
    }

    public void setRibName(String ribName) {
        this.ribName = ribName;
    }
}
