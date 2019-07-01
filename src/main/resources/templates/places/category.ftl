<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->
<#assign title>
</#assign>
<#assign description></#assign>
<#assign category = "places">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "places">
<@layout.extends name="layouts/placesHomepage.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
	<div id="" class="row">
        <div class="col-lg-3">
        </div>
        <div class="col-lg-9" style="border: 1px #CCC solid;">
                    <ul>
                    <#list pages as page>
                        <li><a href="${ webPage.baseUrl! }/places/${ page.slug }">${ page.title }</a></li>
                    </#list>
                    </ul>
        
<!--   <#list placeMainTypes as placeMainType> -->
<!--                         #${ placeMainType }<br> -->
<!--                         places.mainType.${ placeMainType }=<br> -->
<!--                         <#list placeTypes as placeType> -->
<!--                             <#if placeType.mainType! == placeMainType! && placeType != 'ALL' && placeMainType != 'NOTSET'> -->
<!-- places.type.${ placeType }=<br> -->
<!--                             </#if> -->
<!--                         </#list> -->
<!--                     </#list> -->
        </div>
    </div>
    <!-- # -->
    </@layout.put>
    <@layout.put block="footer">
    </@layout.put>
</@layout.extends>
