package com.zyw.novelGame.bussiness.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyw.novelGame.bussiness.service.BookService;
import com.zyw.novelGame.bussiness.service.CatagoryService;
import com.zyw.novelGame.bussiness.service.ModelService;
import com.zyw.novelGame.model.Book;
import com.zyw.novelGame.model.Catagory;
import com.zyw.novelGame.model.Model;
import com.zyw.utils.Utils;

import freemarker.template.Configuration;

@Controller
@RequestMapping("/model")
public class ModelContronller {
	
	public static final  Logger logger=LoggerFactory.getLogger(ModelContronller.class);
	
	@Autowired
	private  Configuration configuration;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private CatagoryService catagoryService;
	
	@RequestMapping(value="/hot",method= {RequestMethod.GET})
	public String hot(HttpServletRequest request,ModelMap  model) {
		CompletableFuture<List<Book>> bookFuture=null;
		CompletableFuture<List<Model>> modelFuture=null;
		CompletableFuture<List<Catagory>> catagoryFuture=null;
		CompletableFuture<PageInfo<HashMap>> bookUpdateInfoFuture=null;
		try {
			bookFuture=CompletableFuture.supplyAsync(()->{
				return bookService.queryBook(6,"create_time",-1);
			});
			
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel();
			});
			catagoryFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(new Catagory());
			});
			bookUpdateInfoFuture=CompletableFuture.supplyAsync(()->{
				PageHelper.startPage(1, 20, true);
				return (new PageInfo<HashMap>(bookService.queryBookUpdateInfo(null,"b.create_time",-1)));
			});
			CompletableFuture.allOf(bookFuture,modelFuture,catagoryFuture,bookUpdateInfoFuture);
			model.addAttribute("bil",bookFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("bul", bookUpdateInfoFuture.get(30,TimeUnit.SECONDS));
			model.addAttribute("mdl", modelFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("cgl", catagoryFuture.get(30,TimeUnit.SECONDS));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Map mp=new HashMap();
		mp.put("bil", model.get("bil"));
		mp.put("bul", model.get("bul"));
		mp.put("mdl", model.get("mdl"));
		mp.put("cgl", model.get("cgl"));
		Utils.saveHtml(configuration,request, "model\\hot\\index", "hot", mp);
		return "hot";
	}
	
	
	
	@RequestMapping(value="/recommend",method= {RequestMethod.GET})
	public String recommend(HttpServletRequest request,ModelMap  model) {
		CompletableFuture<List<Book>> bookFuture=null;
		CompletableFuture<List<Model>> modelFuture=null;
		CompletableFuture<List<Catagory>> catagoryFuture=null;
		CompletableFuture<PageInfo<HashMap>> bookUpdateInfoFuture=null;
		try {
			bookFuture=CompletableFuture.supplyAsync(()->{
				return bookService.queryBook(6,"hits",-1);
			});
			
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel();
			});
			catagoryFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(new Catagory());
			});
			bookUpdateInfoFuture=CompletableFuture.supplyAsync(()->{
				PageHelper.startPage(1, 24, true);
				return new PageInfo<HashMap>(bookService.queryBookUpdateInfo(null,"a.create_time",-1));
			});
			CompletableFuture.allOf(bookFuture,modelFuture,catagoryFuture,bookUpdateInfoFuture);
			model.addAttribute("bil",bookFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("bul", bookUpdateInfoFuture.get(30,TimeUnit.SECONDS));
			model.addAttribute("mdl", modelFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("cgl", catagoryFuture.get(30,TimeUnit.SECONDS));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Map mp=new HashMap();
		mp.put("bil", model.get("bil"));
		mp.put("bul", model.get("bul"));
		mp.put("mdl", model.get("mdl"));
		mp.put("cgl", model.get("cgl"));
		Utils.saveHtml(configuration,request, "model\\recommend\\index", "recommend", mp);
		return "recommend";
	}
	
	@RequestMapping(value="/full",method= {RequestMethod.GET})
	public String full(HttpServletRequest request,ModelMap  model) {
		CompletableFuture<List<Book>> bookFuture=null;
		CompletableFuture<List<Model>> modelFuture=null;
		CompletableFuture<List<Catagory>> catagoryFuture=null;
		CompletableFuture<PageInfo<HashMap>> bookUpdateInfoFuture=null;
		try {
			bookFuture=CompletableFuture.supplyAsync(()->{
				return bookService.queryBook(6,"create_time",0);
			});
			
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel();
			});
			catagoryFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(new Catagory());
			});
			bookUpdateInfoFuture=CompletableFuture.supplyAsync(()->{
				PageHelper.startPage(1, 20, true);
				return new PageInfo<HashMap>(bookService.queryBookUpdateInfo(null,"b.create_time",0));
			});
			CompletableFuture.allOf(bookFuture,modelFuture,catagoryFuture,bookUpdateInfoFuture);
			model.addAttribute("bil",bookFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("bul", bookUpdateInfoFuture.get(30,TimeUnit.SECONDS));
			model.addAttribute("mdl", modelFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("cgl", catagoryFuture.get(30,TimeUnit.SECONDS));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Map mp=new HashMap();
		mp.put("bil", model.get("bil"));
		mp.put("bul", model.get("bul"));
		mp.put("mdl", model.get("mdl"));
		mp.put("cgl", model.get("cgl"));
		Utils.saveHtml(configuration,request, "model\\full\\index", "full", mp);
		return "full";
	}
	
	
	@RequestMapping(value="/authors",method= {RequestMethod.GET})
	public String authors(HttpServletRequest request,ModelMap  model) {
		CompletableFuture<PageInfo<Book>> bookFuture=null;
		CompletableFuture<List<Model>> modelFuture=null;
		CompletableFuture<List<Catagory>> catagoryFuture=null;
		try {
			bookFuture=CompletableFuture.supplyAsync(()->{
				PageHelper.startPage(1,20, true);
				return new PageInfo<Book>(bookService.queryBookByHits());
			});
			
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel();
			});
			catagoryFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(new Catagory());
			});
			CompletableFuture.allOf(bookFuture,modelFuture,catagoryFuture);
			model.addAttribute("bil",bookFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("mdl", modelFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("cgl", catagoryFuture.get(30,TimeUnit.SECONDS));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Map mp=new HashMap();
		mp.put("bil", model.get("bil"));
		mp.put("mdl", model.get("mdl"));
		mp.put("cgl", model.get("cgl"));
		Utils.saveHtml(configuration,request, "model\\authors\\index", "authors", mp);
		return "authors";
	}
	
	@RequestMapping(value="/top",method= {RequestMethod.GET})
	public String top(HttpServletRequest request,ModelMap  model) {
		CompletableFuture<List<Object>> catagoryBookRelationFuture=null;
		CompletableFuture<List<Model>> modelFuture=null;
		CompletableFuture<List<Catagory>> catagoryFuture=null;
		try {
			catagoryBookRelationFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(null);
			}).thenApplyAsync(list->{
				List<Object> li=new ArrayList<Object>();
					list.stream().forEach(catagory->{
						if(!catagory.getCataId().equalsIgnoreCase("0")) {//排除首页
						li.add(bookService.queryBookRelationByCataNameEn(catagory.getCataNameEn(),10));
						}
					});
				return li;
			});
			
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel();
			});
			catagoryFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(new Catagory());
			});
			CompletableFuture.allOf(catagoryBookRelationFuture,modelFuture,catagoryFuture);
			model.addAttribute("tjl", catagoryBookRelationFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("mdl", modelFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("cgl", catagoryFuture.get(30,TimeUnit.SECONDS));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Map mp=new HashMap();
		mp.put("tjl", model.get("tjl"));
		mp.put("mdl", model.get("mdl"));
		mp.put("cgl", model.get("cgl"));
		Utils.saveHtml(configuration,request, "model\\top\\index", "top", mp);
		return "top";
	}
	
	
	@RequestMapping(value="/search",method= {RequestMethod.GET})
	public String search(HttpServletRequest request,ModelMap  model) {
		CompletableFuture<PageInfo<HashMap>> authorBookFuture=null;
		CompletableFuture<List<Model>> modelFuture=null;
		CompletableFuture<List<Catagory>> catagoryFuture=null;
		String keyword=request.getParameter("keyword");
		try {
			authorBookFuture=CompletableFuture.supplyAsync(()->{
				PageHelper.startPage(1,10, true);
				return new PageInfo<HashMap>(bookService.queryBookInfo(keyword,null,keyword,null));
			});
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel();
			});
			catagoryFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(new Catagory());
			});
			CompletableFuture.allOf(authorBookFuture,modelFuture,catagoryFuture);
			model.addAttribute("abl",authorBookFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("mdl", modelFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("cgl", catagoryFuture.get(30,TimeUnit.SECONDS));
			model.addAttribute("keyword",keyword);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Map mp=new HashMap();
		mp.put("abl", model.get("abl"));
		mp.put("mdl", model.get("mdl"));
		mp.put("cgl", model.get("cgl"));
		mp.put("keyword", model.get("keyword"));
		Utils.saveHtml(configuration,request, "model\\search\\index", "search", mp);
		return "search";
		}

}