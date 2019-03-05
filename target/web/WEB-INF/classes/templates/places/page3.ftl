<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
 
<!-- Page Properties -->
<#assign title>
    ${ page.title! }
</#assign>
<#assign description>${ page.description! }</#assign>
<#assign category = "places">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "places">
<!doctype html>
  <html class="places">
   <head>
     <title>${ title! } | <@spring.message "title.brand"/></title>
     <meta name="description" content="${ description! }" />
     <link rel="canonical" href="${ webPage.canonical! }" />
     <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

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
   <div class="container-fluid">
    <div class="row">
        <#include 'comments.ftl'>
        <div class="col-lg-8 col-12">
            <#list page.contents! as content>
                ${ content.text! }
            </#list>
            <ul>
            <#list pages as page>
                <li><a href="${ webPage.baseUrl! }/places/${ page.slug }">${ page.title }</a></li>
            </#list>
            </ul>
        </div>
        <div class="col-lg-4 col-12">
            <#if page.place?has_content>
            
                <ul>
                    <li>${ page.place.type! }, ${ page.place.type.mainType! }</li>
                    <#if page.place.address?has_content>
                        <li>${ page.place.address.address! }</li>
                        <li>${ page.place.address.postCode! }</li>
                        <li>${ page.place.address.city! }</li>
                        <li>${ page.place.address.subregion! }</li>
                        <li>${ page.place.address.region! }</li>
                        <li>${ page.place.address.lat?c },${ page.place.address.lng?c }</li>
                    </#if>
                </ul>
                <ul>
                    <#if page.place.contact?has_content>
                        <li>${ page.place.contact.phone! }</li>
                        <li>${ page.place.contact.whatsapp! }</li>
                        <li>${ page.place.contact.email! }</li>
                        <li>${ page.place.contact.callCanter! }</li>
                        <li>${ page.place.contact.web! }</li>
                    </#if>
                </ul>

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
   <#include '*/common/analytics.ftl'>
   </body>
  </html>