package com.document.documentTranslator.entity;

import com.document.documentTranslator.util.DateUtil;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "documents")
public class Document extends AbstractEntity {

    private static final long serialVersionUID = 6782138390695769805L;

    private String name;
    private Long orderId;
    private String type;
    private String username;

    @Lob
    private byte[] data;

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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public Map<String , Object> map() {

        Map<String , Object> map = super.map();
        map.put("name", this.getName());
        map.put("orderId", this.getOrderId());
        map.put("type", this.getType());
        map.put("creationTime", DateUtil.format(this.getCreationTime()));
        map.put("username", this.getUsername());

        return map;
    }

}

