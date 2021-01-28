package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;

import java.util.Map;

public class OrderDto extends BaseDto {

    private String adminName;
    private Map<String, Object> details;
    private String mode;
    private String status;
    private String type;
    private Long orderId;

    public OrderDto() {
    }

    public void validate() throws DomainException {
        if (Validator.isNull(this.getUsername()))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام کاربری"), ErrorMessage.EMPTY_PARAMETER);
        if (Validator.isNull(this.details))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "جزئیات"), ErrorMessage.EMPTY_PARAMETER);
        if (Validator.isNull(this.type))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نوع سفارش"), ErrorMessage.EMPTY_PARAMETER);
    }

    public void assignValidate() throws DomainException {
        if (Validator.isNull(this.orderId))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "شناسه سفارش"), ErrorMessage.EMPTY_PARAMETER);
        if (Validator.isNull(this.adminName))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "نام مترجم"), ErrorMessage.EMPTY_PARAMETER);
    }

    public void unAssignValidate() throws DomainException {
        if (Validator.isNull(this.orderId))
            throw new DomainException(String.format(ErrorMessage.EMPTY_PARAMETER.getFarsiMessage(), "شناسه سفارش"), ErrorMessage.EMPTY_PARAMETER);
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
