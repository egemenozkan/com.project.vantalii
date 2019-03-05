<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
 
<!-- Page Properties -->
<#assign title>
    ${ page.title! }
</#assign>
<#assign description>
    ${ page.description! }
</#assign>
<#assign category = "events">
<#assign page = "page">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "event">

<!doctype html>
  <html class="events">
   <head>
     <title>${ title! } | <@spring.message "title.brand"/></title>
     <meta name="description" content="${ description! }" />
     <link rel="canonical" href="${ webPage.canonical! }" />
     <#if page.event.localisation??>
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
     <#include '*/common/styles.ftl'>
     <#include '*/common/head.ftl'>
    </head>

<body>
   <#include '*/common/header.ftl'>
   <div class="container">
    <div class="row">
        <div class="col-lg-8 col-12">
            ${ page.event.name }
            <#list page.contents as content>
                ${ content.text! }
            </#list>
        </div>
        <div class="col-lg-4 col-12">
        </div>
        <div class="keywords">
            ${ page.keywords! }
        </div>
    </div>
   
   </div>
   <#include '*/common/footer.ftl'>
   <#include '*/common/javascripts.ftl'>
   <#include '*/common/analytics.ftl'>
   </body>
  </html>