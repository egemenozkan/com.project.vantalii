<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
<!doctype html>
<html  lang="${ webPage.language.code?lower_case }" class="page places" data-language="${ webPage.language }" data-user-id="<#if (user?? &&  user.id??)>${ user.id?c! }<#else>0</#if>">
   <head>
		<@layout.block name="head">
		 <title>${ title! } | <@spring.message "title.brand"/></title>
		 <meta name="description" content="${ description! }" />
		 <link rel="canonical" href="${ webPage.canonical! }" />
		 <link href="https://www.vantalii.com/tr/" hreflang="tr" rel="alternate">
		 <link href="https://www.vantalii.com/en/" hreflang="en" rel="alternate">
		 <link href="https://www.vantalii.ru" hreflang="ru" rel="alternate">
		 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		 <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=contain">
		 <#include '*/common/styles.ftl'>
		 <#include '*/common/head.ftl'>
		</@layout.block>
	</head>
    <body>
    	<div id="app1" class="wrapper">
			<div>
			<@layout.block name="header">
				<#include '*/common/header.ftl'>
			</@layout.block>
			<@layout.block name="contents">
			</@layout.block>
			<@layout.block name="footer">
			</@layout.block>
			<#include '*/common/footer.ftl'>
		</div>
		<!-- end of .wrapper -->
		 <#include '*/common/javascripts.ftl'>
         <#include '*/common/analytics.ftl'>
    </body>
</html>