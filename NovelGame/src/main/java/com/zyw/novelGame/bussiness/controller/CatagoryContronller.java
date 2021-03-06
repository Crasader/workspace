package com.zyw.novelGame.bussiness.controller;

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
import com.zyw.novelGame.model.Catagory;
import com.zyw.novelGame.model.Model;
import com.zyw.novelGame.model.SearchInfo;
import com.zyw.utils.Utils;

import freemarker.template.Configuration;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zyw.novelGame.bussiness.service.BookService;
import com.zyw.novelGame.bussiness.service.CatagoryService;
import com.zyw.novelGame.bussiness.service.ModelService;
import com.zyw.novelGame.bussiness.service.SearchInfoService;

@Controller("pcCatagory")
@RequestMapping("/catagory")
public class CatagoryContronller {
	public static final  Logger logger=LoggerFactory.getLogger(CatagoryContronller.class);
	
	@Autowired
	private CatagoryService catagoryService;
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private  Configuration configuration;
	
	@Autowired
	private SearchInfoService searchInfoService;
	
	
	@RequestMapping(value="/init",method= {RequestMethod.GET})
	public String init(org.springframework.ui.Model model) {
		Map resultMap=new HashMap();
		Map dataMap=new HashMap();
		CompletableFuture<List<Catagory>> catagoryFuture=null;
		CompletableFuture<List<Model>> modelFuture=null;
		try {
			catagoryFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(new Catagory());
			});
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel("0");
			});
			CompletableFuture.allOf(catagoryFuture,modelFuture);
			dataMap.put("cgl", catagoryFuture.get(30,TimeUnit.SECONDS));
			dataMap.put("mdl", modelFuture.get(30,TimeUnit.SECONDS));
			
			

		}catch(Exception e) {
			resultMap.put("errorCode", 10086);
			e.printStackTrace();
		}
		resultMap.put("data", dataMap);
		resultMap.put("errorCode", 200);
		model.addAllAttributes(resultMap);
		return "main";
	}
	
	@RequestMapping(value="/{cataNameEn}",method= {RequestMethod.GET})
	public String queryBookByHits(HttpServletRequest request,ModelMap  model,@PathVariable String cataNameEn) {
		CompletableFuture<List<HashMap>> bookHitsFuture=null;
		CompletableFuture<PageInfo<HashMap>> bookUpdateInfoFuture=null;
		CompletableFuture<List<Model>> modelFuture=null;
		CompletableFuture<List<Catagory>> catagoryFuture=null;
		CompletableFuture<List<SearchInfo>> searchInfoFuture=null;
		try {
			bookHitsFuture=CompletableFuture.supplyAsync(()->{
				return bookService.queryBookRelationByCataNameEn(cataNameEn,6);
			});
			bookUpdateInfoFuture=CompletableFuture.supplyAsync(()->{
				PageMethod.startPage(1, 24, true);
				return new PageInfo<HashMap>(bookService.queryBookUpdateInfo(cataNameEn,"a.create_time",-1));
			});
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel("0");
			});
			catagoryFuture=CompletableFuture.supplyAsync(()->{
				return catagoryService.queryCatagory(new Catagory());
			});
			searchInfoFuture=CompletableFuture.supplyAsync(()->{
				return searchInfoService.querySearchInfo(new SearchInfo());
			});
			CompletableFuture.allOf(bookHitsFuture,bookUpdateInfoFuture,modelFuture,catagoryFuture,searchInfoFuture);
			model.addAttribute("bcl", bookHitsFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("bul", bookUpdateInfoFuture.get(30,TimeUnit.SECONDS));
			model.addAttribute("mdl", modelFuture.get(30, TimeUnit.SECONDS));
			model.addAttribute("cgl", catagoryFuture.get(30,TimeUnit.SECONDS));
			model.addAttribute("sif", searchInfoFuture.get(30,TimeUnit.SECONDS));
			Map mp=new HashMap();
			mp.put("bcl", model.get("bcl"));
			mp.put("bul", model.get("bul"));
			mp.put("mdl", model.get("mdl"));
			mp.put("cgl", model.get("cgl"));
			mp.put("sif", model.get("sif"));
			Utils.saveHtml(configuration,request, "catagory/"+cataNameEn+"/index", "catagory", mp);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "catagory";
		}

}
