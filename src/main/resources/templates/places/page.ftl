<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->
<#assign title ="${ page.title! }" />
<#assign description>
<#compress>
<#if page.description??>
    ${ page.description! }
<#else>
   <#if page.place.type.mainType! == 'LODGING'>
           <@spring.messageArgs "description.places.mainType.${ page.place.type.mainType! }",["${ page.place.name!}"]/>
   <#elseif page.place.type.mainType! == 'TRANSPORT'>
   <#elseif page.place.type.mainType! == 'SHOPPING'>
           <@spring.messageArgs "description.places.mainType.${ page.place.type.mainType! }",["${ page.place.name!}"]/>
   <#elseif page.place.type.mainType! == 'FOOD_AND_BEVERAGE'>
   <#elseif page.place.type.mainType! == 'ATTRACTIONS'>
           <@spring.messageArgs "description.places.mainType.${ page.place.type.mainType! }", "${ page.place.name!}" />
   <#elseif page.place.type.mainType! == 'ENTERTAINMENT'>
   <#else>
            <@spring.message "description.places.mainType.ALL"/>
    </#if>
</#if>    
</#compress>
</#assign>
<#assign category = "places">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "places">

<@layout.extends name="layouts/place.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
	<div class="row no-gutters">
	    <div class="col-lg-12">
	       <ul class="breadcrumb">
	           <li><a href="${ webPage.baseUrl! }/">Vantalii</a><span>></span></li>
	           <#if page.place.address.city?has_content>
	           <li><a href="${ webPage.baseUrl! }/">${ page.place.address.city! }</a><span>></span></li>
	           </#if>
               <#if page.place.address.region?has_content>
               <li><a href="${ webPage.baseUrl! }/">${ page.place.address.region! }</a><span>></span></li>
               </#if>
               <#if page.place.address.subregion?has_content>
               <li><a href="${ webPage.baseUrl! }/">${ page.place.address.subregion! }</a><span>></span></li>
               </#if>
               <li><a href="${ webPage.baseUrl! }/"><@spring.message "places.mainType.${ page.place.type.mainType! }"/></a><span>></span></li>
               <li><a href="${ webPage.baseUrl! }/"><@spring.message "places.type.${ page.place.type! }"/></a><span>></span></li>
               <li><h1>${ page.title! }</h1></li>
	       </ul>
	    </div>
        <div class="col-lg-6 col-12">
            <div class="box pt-0 mb-2">
                <h2>${ page.title! }</h2>
	            <#if page.place?has_content>
	                    <@utils.address address= page.place.address />
	            </#if>
	             <input type="hidden" id="placeId" value="${ placeId }">
            </div>
            <div id="app1">
                <vue-page-gallery></vue-page-gallery>
            </div>
           
        </div>
        <div class="col-lg-6 col-12">
			<div class="box">
                <#list page.contents! as content>
                    ${ content.text! }
                </#list>
                <div class="keywords">
		            ${ page.keywords! }
		        </div>
            </div>
            
            <#if page.place?has_content>
                <ul>
                    <#if page.place.contact?has_content>
                        <li>${ page.place.contact.phone! }</li>
                        <li>${ page.place.contact.whatsapp! }</li>
                        <li>${ page.place.contact.email! }</li>
                        <li>${ page.place.contact.callCenter! }</li>
                        <li>${ page.place.contact.web! }</li>
                    </#if>
                </ul>

            </#if>
        </div>
       <div class="col-lg-6">
           <div class="box">
               <div id="app2">
                    <vue-comments></vue-comments>
                </div>
           </div>

       </div>
                <div class="col-lg-6">
                
                </div>
    </div>
        <!-- /.container -->
    </@layout.put>
    <@layout.put block="footer">
       <#include '*/common/seoPlaces.ftl'>  
    </@layout.put>
</@layout.extends>

