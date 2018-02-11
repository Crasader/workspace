package com.zyw.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zyw.util.Common;
import com.zyw.util.Utils;

public class Controller {
	
	public static List<Map<String,String>> queryShopList(Map paramter) {
		List<Map<String,String>> list=new ArrayList();
		HttpClient httpclient=HttpClients.createDefault();
		HttpGet hp=new HttpGet("https://s.taobao.com/search?q=�ֻ���&bcoffset=4&ntoffset=4&p4ppushleft=1%2C48&s=44&sort=sale-desc");
		hp.getParams().setParameter("http.protocol.allow-circular-redirects", true);
		InputStream in=null;
		try {
			HttpResponse hr=httpclient.execute(hp);
			in=hr.getEntity().getContent();
			Document document  =Jsoup.parse(in, "utf-8","https://s.taobao.com");
			Elements script=document.getElementsByTag("head").get(0).getElementsByTag("script");
			String data=script.get(script.size()-1).data();
			list=Utils.jsonAnaly(data, Common.IS_TMALL,Common.SHOP_LINK,Common.LOCATION,Common.SHOPPING_ID,Common.TITLE,Common.USER_ID,Common.USER_NAME,Common.VIEW_FEE,Common.VIEW_PRICE,Common.VIEW_SALES);
			/*list.parallelStream().forEach(mp->{
				mp.forEach((key,value)->{
					System.out.println(key+":"+value);
				});
			});
			System.out.println(list.size());*/
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return list;
	}
	
	public static List<Map<String,String>> queryShop(List<Map<String,String>> rList) {
		List<Map<String,String>> list=new ArrayList();
		HttpClient httpclient=HttpClients.createDefault();
		HttpGet hp=new HttpGet("https:"+rList.get(0).get(Common.SHOP_LINK)+"&search=y&orderType=hotsell_desc");
		hp.getParams().setParameter("http.protocol.allow-circular-redirects", true);
		InputStream in=null;
		try {
			HttpResponse hr=httpclient.execute(hp);
			System.out.println(hr.getHeaders("url-hash")[0].getValue());
			in=hr.getEntity().getContent();
			Document document  =Jsoup.parse(in, "utf-8","https://s.taobao.com");
			Element totalCount=document.getElementById("J_ShopAsynSearchURL");
			String shopingTotal=queryShopingTotal(hr.getHeaders("url-hash")[0].getValue().substring(0, hr.getHeaders("url-hash")[0].getValue().lastIndexOf("/"))+totalCount.attr("value"));			
			System.out.println(shopingTotal);
			list.parallelStream().forEach(mp->{
				mp.forEach((key,value)->{
					System.out.println(key+":"+value);
				});
			});
			System.out.println(list.size());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return list;
	}
	
	public static String queryShopingTotal(String url) {
		String result="";
		HttpClient httpclient=HttpClients.createDefault();
		HttpGet hp=new HttpGet(url);
		hp.getParams().setParameter("http.protocol.allow-circular-redirects", true);
		InputStream in=null;
		try {
			HttpResponse hr=httpclient.execute(hp);
			in=hr.getEntity().getContent();
			Document document  =Jsoup.parse(in, "utf-8","https://s.taobao.com");
			System.out.println(document);
			Elements totalCount=document.getElementsByClass("search-result");
			System.out.println(totalCount);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return result;
	}

}