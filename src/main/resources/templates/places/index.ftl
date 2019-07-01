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
	       </ul>
	    </div>
        <div class="col-lg-6 col-12">        
           
        </div>
        <div class="col-lg-6 col-12">
			
        </div>
       <div class="col-lg-6">
           <div class="box">
           
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

