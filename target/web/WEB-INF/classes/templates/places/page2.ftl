<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
 
<!-- Page Properties -->
<#assign title>
    ${ page.title! }
</#assign>
<#assign description>
    ${ page.description! }
</#assign>
<#assign category = "places">
<#assign page = "page">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "place">

<!doctype html>
  <html class="places">
   <head>
     <title>${ title! } | <@spring.message "title.brand"/></title>
     <meta name="description" content="${ description! }" />
     <link rel="canonical" href="${ webPage.canonical! }" />
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
     <#include '*/common/styles.ftl'>
     <#include '*/common/head.ftl'>
    </head>
<body>
   <#include '*/common/header.ftl'>
   <div class="container">
    <div class="row">
        <div class="col-lg-8 col-12">
            ${ page.place.name! } -${ page.language! } - ${ page.place.language! }
            <#list page.contents! as content>
                ${ content.text! }
            </#list>
            
        </div>
        <div class="col-lg-4 col-12">
            <#if page.place?has_content>
                ${ page.place.address.address! }
            </#if>
        </div>
        <div class="keywords">
            ${ page.keywords! }
        </div>
        <div class="col-lg-12">
          <ul class="list">

           </ul>
        </div>
    </div>
   
   </div>
   <#include '*/common/footer.ftl'>
   <#include '*/common/javascripts.ftl'>
   <#include '*/common/analytics.ftl'>
   </body>
  </html>