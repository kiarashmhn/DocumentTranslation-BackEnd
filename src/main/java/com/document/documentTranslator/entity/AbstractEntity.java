package com.document.documentTranslator.entity;

import com.document.documentTranslator.enums.CommonMessages;
import com.google.gson.annotations.Expose;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1905122041950251208L;

    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public Map<String, Object> map() {

        Map<String, Object> map = new HashMap<>();
        if (getId() != null) {
            map.put(CommonMessages.ID.getName(), id.toString());
        }
        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
