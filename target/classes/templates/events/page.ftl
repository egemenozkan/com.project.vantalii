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
<@layout.extends name="layouts/event.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
  <div class="row">
    <div class="e-col col-lg-8">
      <h1>
        ${ page.title! }
      </h1>
      <span class="category"><@spring.message "events.type.${ page.event.type }" /></span>
      <address>
     	  <#if page.event.place?has_content>
	          <i class="fas fa-map-marker-alt"></i><span class="font-size-14 pl-1">${  page.event.place.name! }</span><br>
	          <#if page.event.place.address?has_content>
	              <span class="address" data-coords="${ page.event.place.address.lat! }, ${ page.event.place.address.lng! }">${ page.event.place.address.address! } ${ page.event.place.address.postCode! } 
	              ${ page.event.place.address.region! },${ page.event.place.address.subregion! }, ${ page.event.place.address.city! }</span>
	          </#if> 
	      </#if>
      </address>
      <div>
         <div class="date">
         	<i class="fas fa-calendar-day"></i>
            <span>23 Nisan 2019 Çarşamba</span>
 			<i class="far fa-clock"></i>
            <span>18:00</span><#if page.event.periodType! != 'ONEDAY'><span class="period">${  page.event.periodType! } <i class="fas fa-info-circle font-size-10"></i></span></#if>
         </div>
         <ul class="bar">
           <li>
               <a href="#overview">Overview</a>
           </li>
           <li>
           		<a href="#photos">Photos</a>
           </li>
           <li>
            	<a href="#comments">Comments</a>
           </li>
         </ul>
         <div id="overview">
             <div>
                    <#list page.contents! as content>
                        ${ content.text! }
                    </#list>
                </div>

               <div class="keywords">
                    ${ page.keywords! }
                </div>      
         </div>
         
         <div class="photos"></div> 
         <div class="comments"></div> 
      </div>
    </div>
     <div class="e-col col-lg-4">
       <div id="contact">
	         <#if page.event.place?has_content>
	             <ul>
	                 <#if page.event.place.contact??>
	                      <#if page.event.place.contact.phone?has_content><li><i class="fas fa-phone"></i><span>${ page.event.place.contact.phone! }</span></li></#if>
	                      <#if page.event.place.contact.whatsapp?has_content><li><i class="fab fa-whatsapp"></i><span>${ page.event.place.contact.whatsapp! }</span></li></#if>
	                      <#if page.event.place.contact.email?has_content><li><i class="fas fa-at"></i><span>${ page.event.place.contact.email! }</span></li></#if>
	                      <#if page.event.place.contact.callCenter?has_content><li><i class="fas fa-phone-square"></i><span>${ page.event.place.contact.callCenter! }</span></li></#if>
	                      <#if page.event.place.contact.web?has_content><li><i class="fab fa-wpforms"></i><span>${ page.event.place.contact.web! }</span></li></#if>
	                 </#if>
	             </ul>
	         </#if>  
         </div>
    </div>
  </div>  



           <!-- END TEST -->


        
     

     <!-- /.container -->
    </@layout.put>
    <@layout.put block="footer">
    </@layout.put>
</@layout.extends>

