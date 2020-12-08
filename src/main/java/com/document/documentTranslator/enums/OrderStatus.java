package com.document.documentTranslator.enums;


import com.google.common.base.Enums;
import com.google.common.collect.Maps;

import java.util.Map;


public enum OrderStatus {

	ACTIVE(0, "active", "فعال"),
	INACTIVE(1, "inactive", "غیر فعال"),
	IN_PROGRESS(2, "inProgress", "درحال انجام"),
	COMPLETED(3, "completed", "انجام شده"),
	CANCELLED(4, "cancelled", "لغو شده"),
	PENDING(7, "pending", "در انتظار"),
	;

	private int id;
	private String name;
	private String persianName;

	private OrderStatus(int id, String name, String persianName) {

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

	public static OrderStatus lookupByDisplayName(String name) {

		return Enums.getIfPresent(OrderStatus.class, name).orNull();
	}

	private static final Map<String , OrderStatus> nameIndex = Maps.newHashMapWithExpectedSize(OrderStatus.values().length);
	static{
		for (OrderStatus orderStatus : OrderStatus.values()){
			nameIndex.put(orderStatus.getName(), orderStatus);
		}
	}

	public static OrderStatus lookupByName(String name) {

		return nameIndex.get(name);
	}

	private static final Map<String , OrderStatus> persianNameIndex = Maps.newHashMapWithExpectedSize(OrderStatus.values().length);
	static{
		for (OrderStatus orderStatus : OrderStatus.values()){
			persianNameIndex.put(orderStatus.getPersianName(), orderStatus);
		}
	}

	public static OrderStatus lookupByPersianName(String name) {

		return persianNameIndex.get(name);
	}

	private static final Map<Integer , OrderStatus> idIndex = Maps.newHashMapWithExpectedSize(OrderStatus.values().length);
	static{
		for (OrderStatus orderStatus : OrderStatus.values()){
			idIndex.put(orderStatus.getId(), orderStatus);
		}
	}

	public static OrderStatus lookupById(int id) {

		return idIndex.get(id);
	}

}
