<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
<!doctype html>
<html lang="${ webPage.language.code }">
   <head>
		<@layout.block name="head">
		 <title>${ title! } | <@spring.message "title.brand"/></title>
		 <meta name="description" content="${ description! }" />
		 <link rel="canonical" href="${ webPage.canonical! }" />
		 <link href="https://www.vantalii.com/tr/events" hreflang="tr" rel="alternate">
		 <link href="https://www.vantalii.com/en/events" hreflang="en" rel="alternate">
		 <link href="https://www.vantalii.ru/events" hreflang="ru" rel="alternate">
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