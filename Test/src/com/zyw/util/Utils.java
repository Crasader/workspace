package com.zyw.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
	
	public static List<Map<String,String>> strAnaly(String content,String... paramters) {
		List<Map<String,String>> list=new ArrayList();
		Map<String,String> productMap=new HashMap();
		int startIndex=0;
		int endIndex=0;
		String s1="";
		for(int i=0;i<content.length();i++) {
			for(String str:paramters) {
				startIndex=content.indexOf(str, i)+str.length();
				s1=content.substring(startIndex,content.length());
				endIndex=s1.indexOf(",",0);
				productMap.put(str, content.substring(startIndex,endIndex));
				
			}
			list.add(productMap);
		}
		
		return list;
		
	}

}