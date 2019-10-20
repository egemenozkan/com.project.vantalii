<#ftl encoding="utf-8">

<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->
<#assign title>
    ${ page.title! }
</#assign>
<#assign description>${ page.description! }</#assign>
<#assign category = "places">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "places">
<#assign img ="https://blogmedia.evbstatic.com/wp-content/uploads/wpmulti/sites/3/2018/03/03103805/big-concert-audience-listening-to-music-at-festival-picture-id485343244.jpg">
<@layout.extends name="layouts/place.ftl"> <@layout.put block="header">
</@layout.put> <@layout.put block="contents">
<div class="hero">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1>${ page.title! }</h1>
<!-- 				<ul class="breadcrumb"> -->
<!-- 		           <li><a href="${ webPage.baseUrl! }/">Vantalii</a><span>></span></li> -->
<!-- 		           <#if page.place.address.city?has_content> -->
<!-- 		           <li><a href="${ webPage.baseUrl! }/">${ page.place.address.city! }</a><span>></span></li> -->
<!-- 		           </#if> -->
<!-- 	               <#if page.place.address.region?has_content> -->
<!-- 	               <li><a href="${ webPage.baseUrl! }/">${ page.place.address.region! }</a><span>></span></li> -->
<!-- 	               </#if> -->
<!-- 	               <#if page.place.address.subregion?has_content> -->
<!-- 	               <li><a href="${ webPage.baseUrl! }/">${ page.place.address.subregion! }</a><span>></span></li> -->
<!-- 	               </#if> -->
<!-- 	               <li><h1>${ page.title! }</h1></li> -->
<!-- 	       		</ul> -->
	       		<span class="category">
	       			<a href="${ webPage.baseUrl! }/places/m/${ page.place.type.mainType.slug }"><@spring.message "places.mainType.${ page.place.type.mainType! }" /></a>
	       		</span>
	       		<span class="category">
	       			<i class="fas fa-greater-than"></i>
	       			<a href="${ webPage.baseUrl! }/places/t/${ page.place.type.slug }"><@spring.message "places.type.${ page.place.type! }" /></a>
	       		</span>
	       		<span class="place">
	       		<i class="fas fa-map-marker-alt"></i>
	     	  	<#if page.place?has_content>
		          	<a href="${ webPage.baseUrl! }/">${ page.place.address.city! }</a> 
		        </#if>
				<#if page.place.address.district?has_content>
					<i class="fas fa-greater-than"></i> <a href="${ webPage.baseUrl! }/">${ page.place.address.district! }</a>
				</#if>
				<#if page.place.address.region?has_content>
					<i class="fas fa-greater-than"></i> <a href="${ webPage.baseUrl! }/">${ page.place.address.region! }</a>
				</#if>
		        </span>
			</div>
		</div>
	</div>
	<div class="nav-list">
		<div class="container">
			<ul>
				<#if page.contents?has_content ><li><i class="fas fa-info-circle"></i><a href="#overview" class="active"><@spring.message "overview" /></a></li></#if>
				<li><i class="fas fa-camera-retro"></i><a href="#photos"><@spring.message "photos" /></a></li>
				<li><i class="fas fa-comments"></i><a href="#comments"><@spring.message "comments" /></a></li>
				<li><i class="fas fa-map-marked-alt"></i><a href="#address"><@spring.message "address" /></a></li>
			</ul>
		</div>
	</div>
</div>
<div class="container">
	<div class="row mt-2">
	    <div class="e-col col-lg-8">
	    	<#if page.place.startTime?? || page.place.endTime??>
		    	<div class="box">
			        <div class="box-header">
		         
			         </div>
			         <div class="box-body p-1">
				         <div class="date">
				            <div class="time">
				 				<div class="working-hours">
				 					<b><i class="far fa-clock mr-1"></i><@spring.message "places.openingTime" /></b>
				 					<span>${ page.place.startTime! }</span>
				 				</div>
	
				            	<div class="working-hours ml-1">
				 					<b><i class="far fa-clock mr-1"></i><@spring.message "places.closingTime" /></b>
				 					<span>${ page.place.endTime! }</span>
				 				</div>
				            </div>
				         </div>
			        </div>
		        </div>
	        </#if>
	        <#if page.contents?has_content>
		        <div id="overview" class="box">
		         	 <div class="box-header">
		         	 	<h4><@spring.message "overview" /></h4>
		         	 </div>	
		             <div class="box-body">
		                    <#list page.contents! as content>
		                        ${ content.text! }
		                    </#list>
		                </div>
		
		               <div class="keywords">
		                    ${ page.keywords! }
		                </div>      
		         </div>
	         </#if>
	         <div id="photos" class="box">
	         	<div class="box-header">
	         	 	<h4><@spring.message "photos" /></h4>
	         	 </div>	
	             <div class="box-body">
		         	<div id="appGallery">
		                <vue-gallery></vue-gallery>
		            </div>
	            </div>
	         </div> 
	         <div id="comments" class="box">
	         	<div class="box-header">
	         	 	<h4><@spring.message "comments" /></h4>
	         	 </div>	
	             <div class="box-body">
			         <div id="appComments">
			         	<vue-comments></vue-comments>
			         </div>
		         </div>
	         </div>
	      </div>
	    <div class="e-col col-lg-4">
			<div id="address" class="box">
	         	<div class="box-header">
	         	 	<h4><@spring.message "address" /></h4>
	         	 </div>	
	             <div class="box-body">
		         	<#if page.place.address?has_content>
		         		<address>
			         	<#if page.place?has_content>
				        	<i class="fas fa-map-marker-alt"></i><span class="font-size-14 pl-1">${  page.place.name! }</span><br>
				    	</#if>
			              <span class="address" data-coords="${ page.place.address.lat! }, ${ page.place.address.lng! }">${ page.place.address.address! } ${ page.place.address.postCode! } 
			              ${ page.place.address.region! }, ${ page.place.address.district! }/${ page.place.address.city! }</span>
			        	</address>
			        </#if>
			        <#if page.place?has_content>
			         	<div class="contact">
				            <ul>
			                 <#if page.place.contact??>
			                      <#if page.place.contact.phone?has_content><li><i class="fas fa-phone"></i><span>${ page.place.contact.phone! }</span></li></#if>
			                      <#if page.place.contact.whatsapp?has_content><li><i class="fab fa-whatsapp"></i><span>${ page.place.contact.whatsapp! }</span></li></#if>
			                      <#if page.place.contact.email?has_content><li><i class="fas fa-at"></i><span>${ page.place.contact.email! }</span></li></#if>
			                      <#if page.place.contact.callCenter?has_content><li><i class="fas fa-phone-square"></i><span>${ page.place.contact.callCenter! }</span></li></#if>
			                      <#if page.place.contact.web?has_content><li><i class="fab fa-wpforms"></i><span>${ page.place.contact.web! }</span></li></#if>
			                 </#if>
				             </ul>
			             </div>
			         </#if>
		         </div>  
	         </div>
	         <!-- #address --> 
	    </div>
	    <!-- .e-col.col-lg-4 -->
	  </div>
  </div>  
     <!-- /.container -->
    </@layout.put>
    <@layout.put block="footer">
    </@layout.put>
</@layout.extends>

