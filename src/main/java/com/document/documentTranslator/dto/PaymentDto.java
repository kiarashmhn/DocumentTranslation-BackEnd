package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;

public class PaymentDto extends BaseDto {

    private Long orderId;
    private String method;
    private Long amount;
    private Long deliveryType;
    private String code;

    public void validate() throws DomainException {
        if (Validator.isNull(this.getOrderId()) || Validator.isNull(this.getMethod()) || Validator.isNull(this.getUsername()) || Validator.isNull(this.getAmount()) || Validator.isNull(this.getDeliveryType()) || Validator.isNull(this.getCode()))
            throw new DomainException(ErrorMessage.INVALID_INPUT);
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Long deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
