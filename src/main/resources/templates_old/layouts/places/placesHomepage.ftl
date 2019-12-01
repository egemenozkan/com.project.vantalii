<#ftl encoding="utf-8">
<#ftl encoding="utf-8">

<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
<!doctype html>
<html lang="${ webPage.language.code?lower_case }"  class="page places" data-language="${ webPage.language }" data-user-id="<#if (user??)>${ user.id! }<#else>0</#if>" data-place-id="${ pageId!0 }">
   <head>
		<@layout.block name="head">
		 <title>${ title! } | <@spring.message "title.brand"/></title>
		 <meta name="description" content="${ description! }" />
		 <link rel="canonical" href="${ webPage.canonical! }" />
		 <link href="https://www.vantalii.com/tr/places" hreflang="tr" rel="alternate">
		 <link href="https://www.vantalii.com/en/places" hreflang="en" rel="alternate">
		 <link href="https://www.vantalii.ru/places" hreflang="ru" rel="alternate">
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
		<div class="container content-area">
			<@layout.block name="contents">
			</@layout.block>
		</div>
		<@layout.block name="footer">
		</@layout.block>
         <#include '*/common/footer.ftl'>
        </div>
         <#include '*/common/javascripts.ftl'>
         <#include '*/common/analytics.ftl'>
    </body>
</html>