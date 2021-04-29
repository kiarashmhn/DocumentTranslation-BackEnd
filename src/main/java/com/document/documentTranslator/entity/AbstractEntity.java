package com.document.documentTranslator.entity;

import com.document.documentTranslator.enums.CommonMessages;
import com.document.documentTranslator.util.DomainUtil;
import com.google.gson.annotations.Expose;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
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

    @Column(name = "ENABLED")
    protected Boolean enable;

    protected Date creationTime;

    public AbstractEntity() {
        creationTime = new Date();
        enable = Boolean.TRUE;
    }

    public Map<String, Object> map() {

        Map<String, Object> map = new HashMap<>();
        if (getId() != null) {
            map.put(CommonMessages.ID.getName(), id.toString());
            map.put("enable", enable);
            map.put("creationTime", DomainUtil.getFormattedDate(creationTime));
        }
        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
