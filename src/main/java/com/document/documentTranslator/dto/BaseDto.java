package com.document.documentTranslator.dto;


import java.io.Serializable;

public class BaseDto implements Serializable {

    private Long id;

    private int begin = 0;
    private int length = 10;

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

