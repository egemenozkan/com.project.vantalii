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
	    <div class="col-lg-12">
	       <ul class="breadcrumb">
               <li><a href="${ webPage.baseUrl! }/">Vantalii</a><span>></span></li>
               <#if page.event.place.address.city?has_content>
               <li><a href="${ webPage.baseUrl! }/">${ page.event.place.address.city! }</a><span>></span></li>
               </#if>
               <#if page.event.place.address.region?has_content>
               <li><a href="${ webPage.baseUrl! }/">${ page.event.place.address.region! }</a><span>></span></li>
               </#if>
               <#if page.event.place.address.subregion?has_content>
               <li><a href="${ webPage.baseUrl! }/">${ page.event.place.address.subregion! }</a><span>></span></li>
               </#if>
               <#if page.event.type?has_content>
               <li><a href="${ webPage.baseUrl! }/">${ page.event.type! }</a><span>></span></li>
               </#if>
               <li><h1 style="font-size-12">${ page.title! }</h1></li>
           </ul>
	    </div>
        <div class="col-lg-12 no-gutters" >
            <h2>${ page.title! }</h2>
            <div class="row" style="">
                <div  class="col-lg-8 cover-image" style="background: url('${ img }'); ">
                    <img src="${ img }" alt="Image" />
                </div>
                <div class="col-lg-4 detail">
                    <ul>
                        <li>
                            <i class="fas fa-calendar-day"></i>
                            <span>23 Nisan 2019 Çarşamba</span>
                        </li>
                        <li>
                            <i class="far fa-clock"></i>
                            <span>18:00</span><#if page.event.periodType! != 'ONEDAY'><span class="period">${  page.event.periodType! } <i class="fas fa-info-circle font-size-10"></i></span></#if>
                        </li>
                        <li>
                            
                        </li>
                        <li>
                             <#if page.event.place?has_content>
                                <i class="fas fa-map-marker-alt"></i><span>${  page.event.place.name! }</span><br>
			                    <#if page.event.place.address?has_content>
			                        <span class="address" data-coords="${ page.event.place.address.lat! }, ${ page.event.place.address.lng! }">${ page.event.place.address.address! } ${ page.event.place.address.postCode! } 
			                        ${ page.event.place.address.region! },${ page.event.place.address.subregion! }, ${ page.event.place.address.city! }</span>
			                    </#if> 
			                </#if>
                        </li>
                        <li>
                           <#if page.event.place?has_content>
				                <ul>
				                    <#if page.event.place.contact??>
				                         <#if page.event.place.contact.phone??><li><i class="fas fa-phone"></i><span>${ page.event.place.contact.phone! }</span></li></#if>
				                         <#if page.event.place.contact.whatsapp??><li><i class="fab fa-whatsapp"></i><span>${ page.event.place.contact.whatsapp! }</span></li></#if>
				                         <#if page.event.place.contact.email??><li><i class="fas fa-at"></i><span>${ page.event.place.contact.email! }</span></li></#if>
				                         <#if page.event.place.contact.callCenter??><li><i class="fas fa-phone-square"></i><span>${ page.event.place.contact.callCenter! }</span></li></#if>
				                         <#if page.event.place.contact.web??><li><i class="fab fa-wpforms"></i><span>${ page.event.place.contact.web! }</span></li></#if>
				                    </#if>
				                </ul>
				            </#if>  
                        </li>
                    </ul>
                </div>
                <!-- others -->
                <div class="col-lg-8" style=" background: #FFF;">
                    <div class="comments">
                        <div class="header">
                            <h4 class=""><@spring.message "comments" /></h4>
                        </div>
                        <div class="comment-item">
                            <div class="title">Bundan İyisi</div>
                            <div class="text">At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores
                             et quas molestias excepturi sint occaecati cupiditate non provident,
                             similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.
                             </div>
                             <span class="datetime">11.04.2018 17:25</span>
                             <span class="user">Egemen O.</span>
                        </div>
                        <div class="new-comment">
                            <div class="header">
                                <input class="form-control" type="text" placeholder="Title" />
                            </div>
                            <div class="body">
                            <textarea class="form-control" placeholder="Message"></textarea>
                            </div>
                            <div class="footer">
                                <button class="btn btn-sm btn-comment-save btn-primary">Kaydet</button>
                            </div>
                        </div>    
                    </div>
                </div>
                <div class="col-lg-4" style=" background: #FFF;">
                
                </div>
            </div>


                 <div>
                    <#list page.contents! as content>
                        ${ content.text! }
                    </#list>
                </div>

               <div class="keywords">
                    ${ page.keywords! }
                </div>           
        </div>
        <div class="col-lg-3">
        B
        </div>
    </div>


           <!-- END TEST -->


        
     

     <!-- /.container -->
    </@layout.put>
    <@layout.put block="footer">
    </@layout.put>
</@layout.extends>

