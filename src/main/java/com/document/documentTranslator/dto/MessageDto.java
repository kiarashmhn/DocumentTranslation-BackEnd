package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.enums.MessageSender;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.Validator;

public class MessageDto extends BaseDto {

    private Long orderId;
    private String text;
    private Boolean hasFile;
    private MessageSender sender;

    public void validate() throws DomainException {
        boolean b = Validator.notNull(orderId)
                && ((Validator.notNull(text) && !text.isEmpty()) || (Validator.notNull(hasFile) && hasFile))
                && Validator.notNull(sender);
        if (!b)
            throw new DomainException(ErrorMessage.INVALID_INPUT);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getHasFile() {
        return hasFile;
    }

    public void setHasFile(Boolean hasFile) {
        this.hasFile = hasFile;
    }

    public MessageSender getSender() {
        return sender;
    }

    public void setSender(MessageSender sender) {
        this.sender = sender;
    }
}
