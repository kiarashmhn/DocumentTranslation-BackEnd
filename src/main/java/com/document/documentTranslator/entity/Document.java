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
    private Long size;
    private String path;
    private Long orderId;
    private String type;
    private String username;

    public Document() {}

    public Document(String name, Long size, String path, Long orderId, String type, String username) {

        this.name = name;
        this.size = size;
        this.path = path;
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

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    @Override
    public Map<String , Object> map() {

        Map<String , Object> map = super.map();
        map.put("name", this.getName());
        map.put("size", String.valueOf(this.getSize()));
        map.put("orderId", this.getOrderId());
        map.put("type", this.getType());
        map.put("creationTime", DateUtil.format(this.getCreationTime()));
        map.put("username", this.getUsername());

        return map;
    }

}

