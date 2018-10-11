package com.zyw.novelGame.mobile.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyw.novelGame.bussiness.service.BookService;
import com.zyw.novelGame.bussiness.service.ModelService;
import com.zyw.novelGame.model.Catagory;
import com.zyw.novelGame.model.Model;
import com.zyw.utils.Utils;

import freemarker.template.Configuration;

@Controller
@RequestMapping("/mobile/full")
public class FullController {
	public static final  Logger logger=LoggerFactory.getLogger(FullController.class);
	
	@Autowired
	private  Configuration configuration;
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private  BookService bookService;
	
	@RequestMapping(value="",method= {RequestMethod.GET})
	public String init(HttpServletRequest request,ModelMap  model) {
		CompletableFuture<List<Model>> modelFuture=null;
		CompletableFuture<PageInfo<HashMap>> bookUpdateInfoFuture=null;
		try {
			
			modelFuture=CompletableFuture.supplyAsync(()->{
				return modelService.queryModel("1");
			});
			bookUpdateInfoFuture=CompletableFuture.supplyAsync(()->{
				PageHelper.startPage(1, 24, true);
				return new PageInfo<HashMap>(bookService.queryBookUpdateInfo(null,"b.create_time",0));
			});
			CompletableFuture.allOf(modelFuture,bookUpdateInfoFuture);
			model.addAttribute("bul", bookUpdateInfoFuture.get(30,TimeUnit.SECONDS));
			model.addAttribute("mdl", modelFuture.get(30, TimeUnit.SECONDS));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Map mp=new HashMap();
		mp.put("bul", model.get("bul"));
		mp.put("mdl", model.get("mdl"));
		Utils.saveHtml(configuration,request, "mobile/full/index", "mobile/full", mp);
		return "mobile/full";
		}

}