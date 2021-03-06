 <!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<#list bil as bookInfoData>
<title>${bookInfoData.bookName}最新章节,${bookInfoData.bookName}免费在线阅读,txt2小说网</title>
<meta name="keywords" content="${bookInfoData.bookName}最新章节,${bookInfoData.bookName}免费在线阅读,${bookInfoData.authorName}的小说">
<meta name="description" content="txt2小说网提供《${bookInfoData.bookName}》最新章节在线免费阅读，每天更新《${bookInfoData.bookName}》最新章节，页面简洁无弹窗，访问速度快！">
</#list>
<meta name="MobileOptimized" content="240">
<meta name="applicable-device" content="mobile">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="shortcut icon" href="/favicon-txt2.ico">
<meta http-equiv="Cache-Control" content="max-age=300">
<meta http-equiv="Cache-Control" content="no-transform ">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/m.css" >
<script type="text/javascript" src="${request.contextPath}/js/mobile/jq.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/mobile/book.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/me/hits.js"></script>
</head>
<body>
 <#include "main.ftl" >
 <div class="cover">
<#list bil as bookInfoData>
<div id="placeId" value="${bookInfoData.bookId}"/>
    <div class="block" name="${bookInfoData.bookNameEn}">
        <div class="block_img2"><img src="${request.contextPath }${bookInfoData.imageUrl}"  border="0" width="92" height="116" onerror="this.src='${request.contextPath }/images/nocover.jpg'" alt="${bookInfoData.bookName}"></div>
        <div class="block_txt2">
            <p></p><h2>${bookInfoData.bookName}</h2><p></p>
            <p>作者：<a href="/mobile/author/${bookInfoData.authorNameEn}/" >${bookInfoData.authorName}</a></p>
            <p>分类：<a href="/mobile/catagory/${bookInfoData.cataNameEn}/" >${bookInfoData.cataName}小说</a></p>           
       <#if bookInfoData.isCompletion==0>
      <p>状态：已完结</p>
      <#else>
     <p>状态：连载中</p>
      </#if>
            <p>更新：${bookInfoData.updateTime?string("yyyy-MM-dd")}</p>
            <p>最新：<a href="/mobile/book/${bookInfoData.bookNameEn}/${bookInfoData.lastStoreId}/"  title="${bookInfoData.storeName}">${bookInfoData.storeName}</a></p>
        </div>
    </div>
    <div class="intro">简介</div>
    <div class="intro_info">
        <p>${bookInfoData.bookDesc}</p>
    </div>
    <div class="intro">正文</div>
    <ul class="chapter">
    <#list sil.list as storeData>
        <li><a href="/mobile/book/${storeData.bookNameEn}/${storeData.storeId}/" >${storeData.storeName}</a></li>
    </#list>
    </ul>
    </#list>
    <div class="listpage" value=${sil.pages?replace(",","")}>
    <span class="left"><a href="javascript:void(0);" class="before" value=1 onclick="changeClick(this,-1)">上一页</a></span>
    <span class="middle">
    <select id="pageselect" onchange="changeClick(options[selectedIndex].value,-999)">
    <#list 1..(sil.pages?replace(",","")) ?eval as i>
    <option value="${i}" >第${i}页</option>
    </#list>
    </select>
    </span>
    <span class="right"><a href="javascript:void(0);" class="onclick" value=1 onclick="changeClick(this,1)">下一页</a></span></div>
</div>
 <#include "foots.ftl">
    </body>
    <div></div>
</html>