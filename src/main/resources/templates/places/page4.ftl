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
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=contain">
     <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.js"></script>

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
     <style>
body {
  padding: 20px;
  font-family: Helvetica;
}

ul {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  grid-gap: 10px;
  margin-left: 0;
}

li {
  background-color: #f5f5f5;
  border-radius: 3px;
  padding: 20px;
  font-size: 14px;
  list-style: none;
}

#comments textarea {
    z-index: auto;
    position: relative;
    line-height: normal;
    font-size: 14px;
    transition: none 0s ease 0s;
    background: transparent !important;
    border: 1px solid #CCC;
    width:100%;
}

#comments button {
    border-color: #078171;
    border-radius: 3px;
    background-color: #078171;
    color: #fff;
}}
     
     </style>
    </head>
<body>
   <#include '*/common/header.ftl'>
   <div class="container-fluid">
    <div class="row">
        <div class="col-lg-8 col-12">
            <h1>${ title }</h1>
                <#include 'comments.ftl'>

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