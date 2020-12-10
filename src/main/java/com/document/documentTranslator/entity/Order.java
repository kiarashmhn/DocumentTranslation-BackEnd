package com.document.documentTranslator.entity;

import com.document.documentTranslator.enums.OrderStatus;
import com.document.documentTranslator.enums.OrderType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    private String username;
    private String details;
    private String adminName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OrderType type;

    public Order() {
    }

    @Override
    public Map<String, Object> map() {
        Map<String, Object> map = super.map();
        map.put("username", this.username);
        map.put("details", this.details);
        map.put("adminName", this.adminName);
        map.put("status", this.status.getPersianName());
        map.put("type", this.type.getPersianName());

        return map;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
