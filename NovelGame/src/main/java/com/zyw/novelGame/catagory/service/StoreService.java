package com.zyw.novelGame.catagory.service;

import java.util.HashMap;
import java.util.List;

import com.zyw.novelGame.model.Store;
import com.zyw.novelGame.model.StoreData;

public interface StoreService {
	
	List<HashMap> queryBookStore(String bookNameEn,String storeId);
	
	int insert(Store record);
	
	int insertStoreData(StoreData storeData);
	
	List<HashMap>  queryBookStoreData(String storeId);

}
