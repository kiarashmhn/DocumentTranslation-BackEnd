package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.enums.OrderType;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;

import java.util.Map;

public class OrderDto extends BaseDto {

    private String username;
    private String adminName;
    private Map<String, Object> details;
    private String status;
    private String type;
    private Long orderId;

    public OrderDto() {
    }

    public void validate() throws DomainException {
        if (Validator.isNull(this.username))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.EMPTY_PARAMETER);
        if (Validator.isNull(this.details))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "جزئیات"), ErrorMessage.EMPTY_PARAMETER);
        if (Validator.isNull(this.type))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نوع سفارش"), ErrorMessage.EMPTY_PARAMETER);
        if (Validator.isNull(OrderType.lookupByName(this.type)))
            throw new DomainException(String.format(ErrorMessage.INVALID_PARAMETER.getFarsiMessage(), "نوع سفارش"), ErrorMessage.INVALID_PARAMETER);
    }

    public void assignValidate() throws DomainException {
        if (Validator.isNull(this.orderId))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "شناسه سفارش"), ErrorMessage.EMPTY_PARAMETER);
        if (Validator.isNull(this.adminName))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام مترجم"), ErrorMessage.EMPTY_PARAMETER);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
