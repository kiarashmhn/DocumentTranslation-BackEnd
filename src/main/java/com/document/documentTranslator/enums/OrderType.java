package com.document.documentTranslator.enums;

import com.google.common.base.Enums;
import com.google.common.collect.Maps;

import java.util.Map;

public enum OrderType {

    ID_CERTIFICATE(0, "identificationCertificate", "شناسنامه"),
    ;

    private int id;
    private String name;
    private String persianName;

    private OrderType(int id, String name, String persianName) {

        this.id = id;
        this.name = name;
        this.persianName = persianName;

    }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String getPersianName() {

        return persianName;
    }

    public static OrderType lookupByDisplayName(String name) {

        return Enums.getIfPresent(OrderType.class, name).orNull();
    }

    private static final Map<String , OrderType> nameIndex = Maps.newHashMapWithExpectedSize(OrderType.values().length);
    static{
        for (OrderType type : OrderType.values()){
            nameIndex.put(type.getName(), type);
        }
    }

    public static OrderType lookupByName(String name) {

        return nameIndex.get(name);
    }

    private static final Map<String , OrderType> persianNameIndex = Maps.newHashMapWithExpectedSize(OrderType.values().length);
    static{
        for (OrderType type : OrderType.values()){
            persianNameIndex.put(type.getPersianName(), type);
        }
    }

    public static OrderType lookupByPersianName(String name) {

        return persianNameIndex.get(name);
    }

    private static final Map<Integer , OrderType> idIndex = Maps.newHashMapWithExpectedSize(OrderType.values().length);
    static{
        for (OrderType type : OrderType.values()){
            idIndex.put(type.getId(), type);
        }
    }

    public static OrderType lookupById(int id) {

        return idIndex.get(id);
    }
}
