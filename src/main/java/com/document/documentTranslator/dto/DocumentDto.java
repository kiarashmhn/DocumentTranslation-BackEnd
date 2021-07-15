package com.document.documentTranslator.dto;

import com.document.documentTranslator.enums.ErrorMessage;
import com.document.documentTranslator.exception.DomainException;
import com.document.documentTranslator.util.DomainUtil;
import com.document.documentTranslator.util.Validator;

import java.util.Map;

public class DocumentDto extends BaseDto {

    private String name;
    private Long size;
    private String path;
    private Long orderId;
    private Long messageId;
    private String type;
    private String useCase;

    public static DocumentDto fromMap(Map<String, Object> map) throws DomainException {
        if (Validator.isNull(map))
            throw new DomainException(ErrorMessage.INVALID_INPUT);

        DocumentDto documentDto = new DocumentDto();
        documentDto.setName(DomainUtil.objectToString(map.get("name")));
        documentDto.setUsername(DomainUtil.objectToString(map.get("username")));
        documentDto.setOrderId(DomainUtil.objectToLong(map.get("orderId")));
        documentDto.setType(DomainUtil.objectToString(map.get("type")));
        documentDto.setSize(DomainUtil.objectToLong(map.get("size")));
        documentDto.setMessageId(DomainUtil.objectToLong(map.get("messageId")));
        documentDto.setUseCase(DomainUtil.objectToString(map.get("useCase")));

        return documentDto;
    }

    public void validate() throws DomainException {
        if (Validator.isNull(this.getOrderId()) || Validator.isNull(this.getType()) || Validator.isNull(this.getSize()) || Validator.isNull(this.getName()))
            throw new DomainException(ErrorMessage.INVALID_INPUT);
    }

    public DocumentDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getUseCase() {
        return useCase;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }
}
