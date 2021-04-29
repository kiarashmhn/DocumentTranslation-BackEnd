package com.document.documentTranslator.entity;

import com.document.documentTranslator.util.DateUtil;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "documents")
public class Document extends AbstractEntity {

    private static final long serialVersionUID = 6782138390695769805L;

    private String name;
    private Long orderId;
    private Long messageId;
    private String type;
    private String username;
    private String path;
    private Long size;

    public Document() {}

    public Document(String name, Long orderId, String type, String username) {

        this.name = name;
        this.orderId = orderId;
        this.type = type;
        this.username = username;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
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

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public Map<String , Object> map() {

        Map<String , Object> map = super.map();
        map.put("name", this.getName());
        map.put("orderId", this.getOrderId());
        map.put("type", this.getType());
        map.put("creationTime", DateUtil.format(this.getCreationTime()));
        map.put("username", this.getUsername());
        map.put("path", this.getPath());
        map.put("size", this.getSize());

        return map;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}

