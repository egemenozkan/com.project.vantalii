<#ftl encoding="utf-8">

<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->
<#assign title>
    ${ page.title! }
</#assign>
<#assign description>${ page.description! }</#assign>
<#assign category = "events">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "events">
<#assign img ="https://blogmedia.evbstatic.com/wp-content/uploads/wpmulti/sites/3/2018/03/03103805/big-concert-audience-listening-to-music-at-festival-picture-id485343244.jpg">
<@layout.extends name="layouts/event.ftl"> <@layout.put block="header">
</@layout.put> <@layout.put block="contents">
<div class="hero">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1>${ page.title! }</h1>
				<span class="category">
	      			<a href="#"><@spring.message "events.type.${ page.event.type }" /></a>
      			</span>
	      		<span class="place">
	     	  	<#if page.event.place??>
		          <i class="fas fa-map-marker-alt"></i><a href="${ webPage.baseUrl! }/${ page.event.place.slug! }" class="font-size-14 pl-1">${ page.event.place.name! }</a>
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
	      <div class="box">
	         <div class="box-header">
	         	<div class="day">
		         	<i class="fas fa-calendar-day"></i>
		            <#if page.event.periodType! != 'ONEDAY'>
		            	<span><@spring.message "events.periodType.${ page.event.periodType }" /></span>
		            	<#else>
		            	<span><@formatter.localDate page.event.startDate 'dd MMMM yyyy EEEE' /> </span>
		            </#if>
	            </div>	         
	         </div>
	         <div class="box-body p-1">
		         <div class="date">
		            <div class="time">
			 			<#if page.event.showStartTime>
			 				<div class="working-hours">
			 					<b><i class="far fa-clock mr-1"></i><@spring.message "events.startTime" /></b>
			 					<span>${ page.event.startTime }</span>
			 				</div>
			            </#if>
			            <#if page.event.showStartTime && page.event.showEndTime>
			            
			            </#if>
			            <#if page.event.showEndTime>
			            	<div class="working-hours ml-1">
			 					<b><i class="far fa-clock mr-1"></i><@spring.message "events.endTime" /></b>
			 					<span>${ page.event.endTime }</span>
			 				</div>
			            </#if>
		            </div>
		         </div>
	         </div>
	         </div>
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
		         	<#if page.event.place.address?has_content>
		         		<address>
			         	<#if page.event.place?has_content>
				        	<i class="fas fa-map-marker-alt"></i><span class="font-size-14 pl-1">${  page.event.place.name! }</span><br>
				    	</#if>
			              <span class="address" data-coords="${ page.event.place.address.lat! }, ${ page.event.place.address.lng! }">${ page.event.place.address.address! } ${ page.event.place.address.postCode! } 
			              ${ page.event.place.address.region! },${ page.event.place.address.subregion! }, ${ page.event.place.address.city! }</span>
			        	</address>
			        </#if>
			        <#if page.event.place?has_content>
			         	<div class="contact">
				            <ul>
			                 <#if page.event.place.contact??>
			                      <#if page.event.place.contact.phone?has_content><li><i class="fas fa-phone"></i><span>${ page.event.place.contact.phone! }</span></li></#if>
			                      <#if page.event.place.contact.whatsapp?has_content><li><i class="fab fa-whatsapp"></i><span>${ page.event.place.contact.whatsapp! }</span></li></#if>
			                      <#if page.event.place.contact.email?has_content><li><i class="fas fa-at"></i><span>${ page.event.place.contact.email! }</span></li></#if>
			                      <#if page.event.place.contact.callCenter?has_content><li><i class="fas fa-phone-square"></i><span>${ page.event.place.contact.callCenter! }</span></li></#if>
			                      <#if page.event.place.contact.web?has_content><li><i class="fab fa-wpforms"></i><span>${ page.event.place.contact.web! }</span></li></#if>
			                 </#if>
				             </ul>
			             </div>
			         </#if>
		         </div>  
	         </div> 
	      </div>
	    </div>
	  </div>  
     <!-- /.container -->
    </@layout.put>
    <@layout.put block="footer">
    </@layout.put>
</@layout.extends>

