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
    private String type;

    public static DocumentDto fromMap(Map<String, Object> map) throws DomainException {
        if (Validator.isNull(map))
            throw new DomainException(ErrorMessage.INVALID_INPUT);

        DocumentDto documentDto = new DocumentDto();
        documentDto.setName(DomainUtil.objectToString(map.get("name")));
        documentDto.setUsername(DomainUtil.objectToString(map.get("username")));
        documentDto.setOrderId(DomainUtil.objectToLong(map.get("orderId")));

        return documentDto;
    }

    public void validate() throws DomainException {
        if (Validator.isNull(this.getOrderId()) || Validator.isNull(this.getUsername()) || Validator.isNull(this.getName()))
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
}
