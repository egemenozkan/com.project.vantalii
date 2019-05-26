<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>

<!-- Page Properties -->
<#assign title>
    <@spring.message "page.home.title" />
</#assign>
<#assign description>
    <@spring.message "page.home.description" />
</#assign>
<#assign category = "home">
<#assign page = "index">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "index">

 
 
 <!doctype html>
  <html>
   <head>
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
 <style>
 body {
  padding: 20px;
  font-family: Helvetica;
}

ul {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  grid-gap: 10px;
}

li {
  background-color: #fff;
  border-radius: 3px;
  padding: 20px;
  font-size: 14px;
}

 </style>
</head>

<body>
   <#include '*/common/header.ftl'>
   <div class="container">

   
   </div>
   <#include '*/common/footer.ftl'>
   <#include '*/common/javascripts.ftl'>
   <#include '*/common/analytics.ftl'>
   </body>
  </html>