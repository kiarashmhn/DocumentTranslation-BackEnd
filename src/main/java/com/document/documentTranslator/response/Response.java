package com.document.documentTranslator.response;


import com.document.documentTranslator.enums.ResponseMessages;

public class Response {

    private String message;
    private Object data;
    private boolean success;
    private Long count;

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Response(String message, Object data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public Response(String message, Object data, boolean success, Long count) {
        this.message = message;
        this.data = data;
        this.success = success;
        this.count = count;
    }

    public Response(ResponseMessages responseMessage, Object data, boolean success, Long count) {
        this.message = responseMessage.getPersianMessage();
        this.data = data;
        this.success = success;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Response() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
