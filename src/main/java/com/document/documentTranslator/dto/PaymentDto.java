package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;

public class PaymentDto extends BaseDto {

    private Long orderId;
    private String method;

    public void validate() throws DomainException {
        if (Validator.isNull(this.getOrderId()) || Validator.isNull(this.getMethod()) || Validator.isNull(this.getUsername()))
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
}
