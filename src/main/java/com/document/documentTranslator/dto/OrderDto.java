package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.enums.OrderStatus;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDto extends BaseDto {

    private String adminName;
    private Map<String, Object> details;
    private String mode;
    private String status;
    private String type;
    private String identifier;
    private Long orderId;
    private Boolean enabled;
    private Long finalDocumentId;
    private Date fromDate;
    private List<OrderStatus> statuses;
    private Long preBillAmount;
    private Long preBillDelay;

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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getFinalDocumentId() {
        return finalDocumentId;
    }

    public void setFinalDocumentId(Long finalDocumentId) {
        this.finalDocumentId = finalDocumentId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public List<OrderStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<OrderStatus> statuses) {
        this.statuses = statuses;
    }

    public Long getPreBillAmount() {
        return preBillAmount;
    }

    public void setPreBillAmount(Long preBillAmount) {
        this.preBillAmount = preBillAmount;
    }

    public Long getPreBillDelay() {
        return preBillDelay;
    }

    public void setPreBillDelay(Long preBillDelay) {
        this.preBillDelay = preBillDelay;
    }
}
