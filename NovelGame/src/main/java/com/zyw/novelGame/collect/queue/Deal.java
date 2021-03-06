package com.zyw.novelGame.collect.queue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zyw.novelGame.bussiness.service.AuthorService;
import com.zyw.novelGame.bussiness.service.BookService;
import com.zyw.novelGame.bussiness.service.CataBookRelationService;
import com.zyw.novelGame.bussiness.service.CatagoryService;
import com.zyw.novelGame.bussiness.service.StoreService;
import com.zyw.novelGame.model.Author;
import com.zyw.novelGame.model.Book;
import com.zyw.novelGame.model.CataBookRelation;
import com.zyw.novelGame.model.Catagory;
import com.zyw.novelGame.model.Store;
import com.zyw.novelGame.model.StoreData;
import com.zyw.utils.HttpConnectionPoolUtil;
import com.zyw.utils.JsoupParse;
import com.zyw.utils.PingyingUtil;
import com.zyw.utils.Utils;
@Component
@Scope("prototype")
public class Deal {
	public static final  Logger logger=LoggerFactory.getLogger(Deal.class);
	@Autowired
	private CatagoryService catagoryService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private CataBookRelationService cataBookRelationService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private Producer producer;
		
	private HttpGet httpget=null;
	private CloseableHttpClient httpClient=null;
	private HttpResponse response=null;
	private Document doc=null;
	private Book book=null;
	private Author author=null;
	private String bookName="";
	private String authorName="";
	private String isCompletion="";
	private String updateTime="";
	private String bookDesc="";
	private String imageName="";
	private String imageUrl="";
	private Catagory catagory=null;
    private CataBookRelation cataBookRelation=null;
			
	public  void init(QueueInfo queueInfo){
		try {
   	 if("0".equalsIgnoreCase(queueInfo.getType())) {
		 deal(queueInfo);
	 }else if("1".equalsIgnoreCase(queueInfo.getType())) {
		 dealBook(queueInfo);
	 }else if("2".equalsIgnoreCase(queueInfo.getType())) {
		 dealStore(queueInfo);
	 }
   	 }catch(Exception e){
		 e.printStackTrace();
	 }
	}
	
	@Transactional
	public  void dealBook(QueueInfo queueInfo) {
		book=new Book();
		author=new Author();
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	     catagory=new Catagory();
       cataBookRelation=new CataBookRelation();
		try {
				 httpClient=HttpConnectionPoolUtil.getHttpClient(queueInfo.getResult().toString());
		    	 httpget = new HttpGet(queueInfo.getResult().toString());  
		         httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");  
		         HttpConnectionPoolUtil.setRequestConfig(httpget);
				 response = httpClient.execute(httpget);
				 doc = Jsoup.parse(EntityUtils.toString(response.getEntity(),queueInfo.getCollect().getNovelCharset()));
				 EntityUtils.consume(response.getEntity());
				 authorName=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getAuthorName().getUrlMatch()).get(0).toString();
         	    book.setAuthorName(authorName.trim());
			    book.setAuthorNameEn(PingyingUtil.ToPinyin(book.getAuthorName()));
			    bookDesc=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getBookDesc().getUrlMatch()).get(0).toString();
				 bookName=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getBookName().getUrlMatch()).get(0).toString();
				 book.setBookName(bookName);
         	     book.setBookNameEn(PingyingUtil.ToPinyin(bookName));
         	     book.setHits(0L);
         	     if( queueInfo.getCollect().getBookInfo().getUpdateTime()==null) {
   				 book.setUpdateTime(new Date());
 			     book.setCreateTime(new Date());
         	     }else {
    				 updateTime=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getUpdateTime().getUrlMatch()).get(0).toString();
    				 book.setUpdateTime(sdf.parse(updateTime.substring(updateTime.indexOf(queueInfo.getCollect().getBookInfo().getUpdateTime().getValueDeal())+1).trim()) );
     			     book.setCreateTime(sdf.parse(updateTime.substring(updateTime.indexOf(queueInfo.getCollect().getBookInfo().getUpdateTime().getValueDeal())+1).trim())  );

         	     }
         	     if(queueInfo.getCollect().getBookInfo().getIsCompletion()==null) {
         	    	book.setIsCompletion(1);
         	     }else {
         	    	 isCompletion=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getIsCompletion().getUrlMatch()).get(0).toString();
    				 if(isCompletion.contains("已完结")) {
         			   book.setIsCompletion(0);
     			   }else {
     				   book.setIsCompletion(1);
     			   }
         	     }
				
				 String cataName=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getCataName().getUrlMatch()).get(0).toString();
				 if(queueInfo.getCollect().getBookInfo().getCataName().getValueDeal()!=null) {
					 if(queueInfo.getCollect().getBookInfo().getCataName().getValueDeal().contains("substr")) {
						 String[] str=queueInfo.getCollect().getBookInfo().getCataName().getValueDeal().split(" ");
						 cataName=cataName.substring(Integer.parseInt(str[1]), Integer.parseInt(str[2]));
					 }
				 }
				 catagory.setCataName(cataName);
		           List<Catagory> cataList=catagoryService.queryCatagory(catagory);
		           if(cataList.size()>0) {
		        	   cataBookRelation.setCataId(cataList.get(0).getCataId());
		           }else {
		        	   cataBookRelation.setCataId("10");
		           }
		            author.setAuthorName(book.getAuthorName());
 		        author.setAuthorNameEn(PingyingUtil.ToPinyin(book.getAuthorName()));
 		        
		if(queueInfo.getCollect().getBookInfo().getStoreCataUrl()!=null) {
		            	 httpClient=HttpConnectionPoolUtil.getHttpClient(queueInfo.getCollect().getNovelSiteUrl());
				    	 httpget = new HttpGet(JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getStoreCataUrl().getUrlMatch()).get(0).toString());  
				         httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");          
				         HttpConnectionPoolUtil.setRequestConfig(httpget);
						 response = httpClient.execute(httpget);
						 doc = Jsoup.parse(EntityUtils.toString(response.getEntity(),queueInfo.getCollect().getNovelCharset()));
						 EntityUtils.consume(response.getEntity());
		}
		
  		   List<HashMap> blist=bookService.queryBookInfo(author.getAuthorName(),author.getAuthorName(),book.getBookName(),book.getBookName());
			   if(blist.size()>0) {
				     Store record=new Store();
				     record.setBookId(blist.get(0).get("bookId").toString());
				     int storeCount=storeService.queryStoreCount(record);
						 List storeList=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getStoreRule().getUrlMatch());
						 for(int i=(storeCount-1);i>-1;i--) {
							 storeList.remove(i);
						 }
						 
						 if(storeList.size()<1) {
							 return;
						 }
						 
						 List<Store> sList=storeService.queryLastStoreIdByBookId(blist.get(0).get("bookId").toString());
					      Map mp=new HashMap();
					      QueueInfo queue=new QueueInfo();
						 mp.put("bookId", blist.get(0).get("bookId").toString());
						 //mp.put("updateTime", book.getUpdateTime());
						 mp.put("storeCount", storeCount);
						 mp.put("preStoreId", (sList.size()==0)?"":sList.get(0).getStoreId());
						 mp.put("currentStoreId", (sList.size()==0)?"":sList.get(0).getNextStoreId());
						 queue.setCollect(queueInfo.getCollect());
						 queue.setType("2");
						 queue.setMark(mp);
						 queue.setResultList(storeList);
						 producer.add(queue);		            
				   return;
			   }
  		   List<Author> alist=authorService.queryAuthorInfo(author);
  		   if(alist.size()>0) {
  			   book.setAuthorId(alist.get(0).getAuthorId());
  		   }else {
  			   author.setAuthorId(UUID.randomUUID().toString());
  			   author.setId(UUID.randomUUID().toString());
  			   authorService.insert(author);
  			   book.setAuthorId(author.getAuthorId());
  		   }
  		       book.setBookId(UUID.randomUUID().toString());
	            book.setId(UUID.randomUUID().toString());
	            book.setBookDesc(bookDesc);
    		    imageUrl=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getImageUrl().getUrlMatch()).get(0).toString();
    		    //logger.info("image url:"+imageUrl);
    		    if(imageUrl.contains(".")) {
    		           imageName=book.getBookId()+imageUrl.substring(imageUrl.lastIndexOf("."));
    		    }else {
    	 	           imageName=book.getBookId();
    		    }
		       book.setImageUrl("/images/data/"+imageName);
		       bookService.insert(book);
		        cataBookRelation.setBookId(book.getBookId());
		             cataBookRelation.setId(UUID.randomUUID().toString());
		             cataBookRelationService.insert(cataBookRelation);
		             if(imageUrl.contains("http")) {
			             Utils.saveImages(imageUrl,book.getImageUrl());

		             }else {
			             Utils.saveImages(queueInfo.getCollect().getNovelSiteUrl()+imageUrl,book.getImageUrl());
		             }
		             if(queueInfo.getCollect().getBookInfo().getStoreCataUrl()!=null) {
		            	 httpClient=HttpConnectionPoolUtil.getHttpClient(queueInfo.getCollect().getNovelSiteUrl());
				    	 httpget = new HttpGet(JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getStoreCataUrl().getUrlMatch()).get(0).toString());  
				         httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");  
				         HttpConnectionPoolUtil.setRequestConfig(httpget);
						 response = httpClient.execute(httpget);
						 doc = Jsoup.parse(EntityUtils.toString(response.getEntity(),queueInfo.getCollect().getNovelCharset()));
						 EntityUtils.consume(response.getEntity());
		             }
				      Map mp=new HashMap();
				      QueueInfo queue=new QueueInfo();
					 mp.put("bookId", book.getBookId());
					 //mp.put("updateTime", book.getUpdateTime());
					 queue.setCollect(queueInfo.getCollect());
					 queue.setType("2");
					 queue.setMark(mp);
					 queue.setResultList(JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getStoreRule().getUrlMatch()));
					 producer.add(queue);
				//logger.info(EntityUtils.toString(response.getEntity()));
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 删除ArrayList中重复元素，保持顺序     
	 public void removeDuplicateWithOrder(List list) {
	    Set set = new HashSet();    
	    List newList = new ArrayList();    
	   for (Iterator iter = list.iterator(); iter.hasNext();) {
	         Object element = iter.next();    
	         if (set.add(element))    
	            newList.add(element);    
	      }     
	     list.clear();    
	     list.addAll(newList);  
		 Collections.sort(list,new Comparator<Object>() {
			 
			 @Override
			 public int compare(Object b1,Object b2) {
				 int a=0;
				 int b=0;
				 if(b1.toString().contains("/")&&b1.toString().contains(".")&&b1.toString().lastIndexOf(".")>b1.toString().lastIndexOf("/")) {
					 a=Integer.parseInt(b1.toString().substring(b1.toString().lastIndexOf("/")+1,b1.toString().lastIndexOf(".")));
					 b=Integer.parseInt(b2.toString().substring(b2.toString().lastIndexOf("/")+1,b2.toString().lastIndexOf(".")));
					 if(a>b) {
						 return 1;
					 }else if(a<b){
						 return -1;
					 }else {
						 return 0;
					 }
				 }else if(b1.toString().contains("/")) {
					 a=Integer.parseInt(b1.toString().substring(b1.toString().lastIndexOf("/", b1.toString().lastIndexOf("/")-1)+1,b1.toString().lastIndexOf("/")));
					 b=Integer.parseInt(b2.toString().substring(b2.toString().lastIndexOf("/", b2.toString().lastIndexOf("/")-1)+1,b2.toString().lastIndexOf("/")));
					 if(a>b) {
						 return 1;
					 }else if(a<b){
						 return -1;
					 }else {
						 return 0;
					 }
				 }
				 return b1.toString().compareTo(b2.toString());
			 }
			 
		 });
	    //System.out.println( " remove duplicate " + list);    
	 }
	
	 @Transactional
	public void dealStore(QueueInfo queueInfo) {
		String preStoreId="";
	    String nextStoreId="";
	    String curentStoreId="";
	    long storeCount=0;
	    long count=1;
		String  storeName="";
		boolean updateFlag=false;
	       try {
    		   if(queueInfo.getMark().get("storeCount")!=null&&Integer.parseInt(queueInfo.getMark().get("storeCount").toString())>0) {
    			   storeCount=Integer.parseInt(queueInfo.getMark().get("storeCount").toString());
    			   preStoreId=queueInfo.getMark().get("preStoreId").toString();
    			   nextStoreId=queueInfo.getMark().get("currentStoreId").toString();
    			   if(nextStoreId.equalsIgnoreCase("0")) {
    				   nextStoreId=UUID.randomUUID().toString();
    				   updateFlag=true;
    			   }
    		   }
    		   removeDuplicateWithOrder(queueInfo.getResultList());
    		   /*Store cStore=new Store();*/
	    	   for(Object item:queueInfo.getResultList()) {	    		  
	    		  /* cStore.setStoreUrl(item.toString());
	    		   cStore.setBookId(queueInfo.getMark().get("bookId").toString());
	    		   if(storeService.queryStoreCount(cStore)>0){
	    			        continue;
	    		   }*/
	    		   if(!item.toString().contains("http")) {
	    			   item=queueInfo.getCollect().getNovelSiteUrl()+item;
	    		   }
				 if(storeCount==0) {
		    		   preStoreId="0";
			    	   curentStoreId=UUID.randomUUID().toString();
		    	   }else {
		    		   curentStoreId=nextStoreId;
		    	   }
		    	   if(count==queueInfo.getResultList().size()) {
		    		   nextStoreId="0";
		    	   }else {
		    		   nextStoreId=UUID.randomUUID().toString();
		    	   }
		        Store store=new Store();
		       StoreData storeData=new StoreData();
	    	   store.setBookId(queueInfo.getMark().get("bookId").toString());
	    	   store.setId(UUID.randomUUID().toString());
	    	   store.setStoreUrl(item.toString());
	    	   store.setPreStoreId(preStoreId);
	    	   store.setNextStoreId(nextStoreId);
	    	   store.setStoreId(curentStoreId);
	    	   httpClient=HttpConnectionPoolUtil.getHttpClient(item.toString());
		    	 httpget = new HttpGet(item.toString());  
		         httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");     
		         HttpConnectionPoolUtil.setRequestConfig(httpget);
				 response = httpClient.execute(httpget);
				 doc = Jsoup.parse(EntityUtils.toString(response.getEntity(),queueInfo.getCollect().getNovelCharset()));
				 EntityUtils.consume(response.getEntity());
		   storeName=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getStoreInfo().getStoreName().getUrlMatch()).get(0).toString();
		   String storeContent=JsoupParse.parse(doc, queueInfo.getCollect().getBookInfo().getStoreInfo().getStoreContent().getUrlMatch()).get(0).toString();
		   if(queueInfo.getCollect().getNovelCharset().equalsIgnoreCase("gbk")) {
		        storeContent=Utils.getUTF8StringFromGBKString(storeContent);
		   }
     	   storeData.setStoreContent(storeContent.getBytes());
     	   storeData.setId(UUID.randomUUID().toString());
     	   storeData.setStoreId(store.getStoreId());
	       store.setStoreName(storeName);
	       //store.setCreateTime((Date) queueInfo.getMark().get("updateTime"));
     	   store.setCreateTime(new Date());
     	   store.setOrderIndex(storeCount+1);
           storeService.insertStoreData(storeData);
           storeService.insert(store);
     	   //更新最新章节
     	   if(count==queueInfo.getResultList().size()||0==(storeCount%10)) {
     		  Book record=new Book();
     		   record.setBookId(store.getBookId());
     		   record.setLastStoreId(store.getStoreId());
     		   record.setUpdateTime(new Date());
     		   bookService.updateByBookID(record);
	    	   }
    	   if(updateFlag) {
    		   store=new Store();
    		   store.setStoreId(preStoreId);
    		   store.setNextStoreId(curentStoreId);
    		   storeService.updateByStoreIdBySelective(store);
    		   updateFlag=false;
    	   }
		    preStoreId=curentStoreId;
	     	 count++;
	     	 storeCount++;
     	   }
	       } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void deal(QueueInfo queueInfo) {		
			 try {
					 httpClient=HttpConnectionPoolUtil.getHttpClient(queueInfo.getResult().toString());
			    	 httpget = new HttpGet(queueInfo.getResult().toString());  
			         httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");      
			         HttpConnectionPoolUtil.setRequestConfig(httpget);
					 response = httpClient.execute(httpget);
					 doc = Jsoup.parse(EntityUtils.toString(response.getEntity(),queueInfo.getCollect().getNovelCharset()));
					 EntityUtils.consume(response.getEntity());
					 QueueInfo queue=null;
					// int count=0;
					 List resultList=JsoupParse.parse(doc, queueInfo.getCollect().getBookRule().getUrlMatch());
					 for(Object item:resultList) {
						 queue=new QueueInfo();
						 queue.setCollect(queueInfo.getCollect());
						 queue.setType("1");
						 queue.setResult(item.toString());
						 producer.add(queue);
/*						 if(count>10) {
							 break;
						 }*/
						// count++;
					 };
					
					 
					//logger.info(EntityUtils.toString(response.getEntity()));
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public static void main(String[] args) {
		System.out.println(35%10);
		//System.out.println("http://dasdasdas/12/".substring("http://dasdasdas/12/".lastIndexOf("/", "http://dasdasdas/12/".lastIndexOf("/")-1)+1, "http://dasdasdas/12/".lastIndexOf("/")));
	}

}
