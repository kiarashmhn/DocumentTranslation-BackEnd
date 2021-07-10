package com.document.documentTranslator.entity;

import com.document.documentTranslator.util.DomainUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "configs")
public class Config extends AbstractEntity {

    private String accName;
    private String accLastName;
    private String accNumber;
    private String address;
    private String questions;

    @Override
    public Map<String, Object> map() {
        Map<String, Object> map = super.map();
        map.put("accName", this.accName);
        map.put("accLastName", this.accLastName);
        map.put("accNumber", this.accNumber);
        map.put("address", this.address);
        map.put("questions", DomainUtil.stringToJson(this.questions));

        return map;
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

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}
