<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
<!doctype html>
<html  lang="${ webPage.language.code?lower_case }" class="page places" data-language="${ webPage.language }" data-user-id="<#if (user??)>${ user.id?c! }<#else>0</#if>" data-place-id="${ page.place.id?c!0 }">
   <head>
		<@layout.block name="head">
		<#compress>
		 <title>${ title! } | <@spring.message "title.brand"/></title>
		 <meta name="description" content="${ description! }" />
		 <link rel="canonical" href="${ webPage.canonical! }" />
		 </#compress>
         <#if page.place.localisation?has_content>
             <#if page.place.localisation['TURKISH']?has_content>
             <link href="https://www.vantalii.com/tr/places/${ page.place.localisation['TURKISH'].slug }" hreflang="tr" rel="alternate">
             </#if>
             <#if page.place.localisation['ENGLISH']?has_content>
             <link href="https://www.vantalii.com/en/places/${ page.place.localisation['ENGLISH'].slug }" hreflang="en" rel="alternate">
             </#if>
             <#if page.place.localisation['RUSSIAN']?has_content>
             <link href="https://www.vantalii.ru/places/${ page.place.localisation['RUSSIAN'].slug }" hreflang="ru" rel="alternate">
             </#if>   
         </#if>
		 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		 <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=contain">
		 <#include '*/common/styles.ftl'>
		 <#include '*/common/head.ftl'>
		</@layout.block>
	</head>
    <body>
        <div class="wrapper">
		<@layout.block name="header">
            <#include '*/common/header.ftl'>
		</@layout.block>
		<@layout.block name="contents">
		</@layout.block>
		<@layout.block name="footer">
		</@layout.block>
		<#include '*/common/footer.ftl'>
		</div>
    </body>
     <#include '*/common/javascripts.ftl'>
     <#include '*/common/analytics.ftl'>
</html>