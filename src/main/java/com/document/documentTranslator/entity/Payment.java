package com.document.documentTranslator.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment extends AbstractEntity {

    private String username;
    private Long orderId;
    private String method;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
