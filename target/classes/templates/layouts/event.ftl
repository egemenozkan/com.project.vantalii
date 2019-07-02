<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
<!doctype html>
<html lang="${ webPage.language.code }" class="page events">
   <head>
		<@layout.block name="head">
		 <title>${ title! } | <@spring.message "title.brand"/></title>
		 <meta name="description" content="${ description! }" />
		 <link rel="canonical" href="${ webPage.canonical! }" />
         <#if page.event.localisation?has_content>
             <#if page.event.localisation['TURKISH']?has_content>
             <link href="https://www.vantalii.com/tr/events/${ page.event.localisation['TURKISH'].slug }" hreflang="tr" rel="alternate">
             </#if>
             <#if page.event.localisation['ENGLISH']?has_content>
             <link href="https://www.vantalii.com/en/events/${ page.event.localisation['ENGLISH'].slug }" hreflang="en" rel="alternate">
             </#if>
             <#if page.event.localisation['RUSSIAN']?has_content>
             <link href="https://www.vantalii.ru/events/${ page.event.localisation['RUSSIAN'].slug }" hreflang="ru" rel="alternate">
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
		<div class="container events">
			<@layout.block name="contents">
			</@layout.block>
		</div>
		<@layout.block name="footer">
		</@layout.block>
		<#include '*/common/footer.ftl'>
		</div>
    </body>
     <#include '*/common/javascripts.ftl'>
     <#include '*/common/analytics.ftl'>
</html>