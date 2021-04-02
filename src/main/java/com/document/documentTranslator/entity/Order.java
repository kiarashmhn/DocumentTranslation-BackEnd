package com.document.documentTranslator.entity;

import com.document.documentTranslator.enums.OrderStatus;
import com.document.documentTranslator.util.DomainUtil;
import com.document.documentTranslator.util.Validator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    private String username;
    private String details;
    private String adminName;
    private Date submitDate;
    private Date acceptanceDate;
    private Date deliveryDate;
    private Boolean isPaid;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "type")
    private String type;

    public Order() {
        isPaid = Boolean.FALSE;
    }

    @Override
    public Map<String, Object> map() {
        Map<String, Object> map = super.map();
        map.put("username", this.username);
        map.put("details", DomainUtil.stringToJson(this.details));
        map.put("adminName", this.adminName);
        map.put("status", Validator.notNull(this.status) ? this.status.name() : null);
        map.put("type", this.type);
        map.put("identifier", this.type + this.getId());
        map.put("submitDate", DomainUtil.getFormattedDate(this.submitDate));
        map.put("acceptanceDate", DomainUtil.getFormattedDate(this.acceptanceDate));
        map.put("deliveryDate", DomainUtil.getFormattedDate(this.deliveryDate));
        map.put("isPaid", this.isPaid);

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
