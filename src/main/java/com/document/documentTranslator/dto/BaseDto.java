package com.document.documentTranslator.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class BaseDto implements Serializable {

    public BaseDto(){
        this.begin = 0;
        this.length = 10;
    }

    private Long id;

    @JsonIgnore
    private int begin;

    @JsonIgnore
    private int length;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

