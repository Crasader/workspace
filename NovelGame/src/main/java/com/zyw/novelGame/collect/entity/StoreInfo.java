package com.zyw.novelGame.collect.entity;

public class StoreInfo {
	
	private Rule storeName;
		
	private Rule storeContent;
	
	private Long orderIndex;
	
	private String storeUrl;
	

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	public Rule getStoreName() {
		return storeName;
	}

	public void setStoreName(Rule storeName) {
		this.storeName = storeName;
	}

	public Rule getStoreContent() {
		return storeContent;
	}

	public void setStoreContent(Rule storeContent) {
		this.storeContent = storeContent;
	}

	public Long getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Long orderIndex) {
		this.orderIndex = orderIndex;
	}
	
}
