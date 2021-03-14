package com.document.documentTranslator.enums;


import com.google.common.base.Enums;
import com.google.common.collect.Maps;

import java.util.Map;


public enum OrderStatus {

	IN_PROGRESS(4, "inProgress", "En cours"),
	COMPLETED(5, "completed", "Fini"),
	DELIVERED(7, "delivered", "Livré"),
	CANCELLED(6, "cancelled", "Annulé"),
	WAITING_FOR_PAYMENT(2, "waitingForPayment", "En attente de paiement"),
	PENDING(3, "pending", "En attente d'acceptation"),
	COMPLETING(1, "completing", "En attente de l'achèvement")
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
