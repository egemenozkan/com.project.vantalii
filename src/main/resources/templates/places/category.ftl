<#ftl encoding="utf-8">

<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->

    <#if mainType?has_content>
        <#assign title>
            <@spring.message "places.mainType.${ mainType! }" />
        </#assign>
    </#if>
    <#if type?has_content>
        <#assign title>
            <@spring.message "places.type.${ type! }" />
        </#assign>
    </#if>

<#assign description></#assign>
<#assign category = "places">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "places">
<@layout.extends name="layouts/placesCategory.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
	<div id="" class="row mt-3">
        <div class="col-lg-12">
            <div class="row places-cards">
                <#list pages as page>
                    <div class="col-4">
                        <div class="card">
                            <img class="card-img-top" src="/static/img/${ page.place.type! }.jpg" alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title"><a href="${ webPage.baseUrl! }/places/${ page.slug }" class="stretched-link">${ page.title }</a></h5>
                                <#if page.place.address??>
                                    <div class="region">
                                        <#if page.place.address.subregion?has_content>
                                            <a href="${ webPage.baseUrl! }/">${ page.place.address.subregion! }</a>
                                        </#if>
                                        <#if page.place.address.subregion?has_content && page.place.address.region?has_content>,</#if>
                                        <#if page.place.address.region?has_content>
                                            <a href="${ webPage.baseUrl! }/">${ page.place.address.region! }</a>
                                        </#if>
                                    </div>
                                </#if>
                                <!--
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                -->
                            </div>
                            <div class="card-body">
                            </div>
                            <div class="card-footer text-muted">
                   <#--               <#if page.place.openingTime?has_content>
                                    ${ page.place.openingTime }
                                </#if>
                                <#if page.place.closingTime?has_content>
                                    ${ page.place.closingTime }
                                </#if>  -->
                                <div class="badge <#if page.place.open>badge-success<#else>badge-danger</#if>"><#if page.place.open>Opened<#else>Closed</#if></div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
    <!-- # -->
    </@layout.put>
    <@layout.put block="footer">
    </@layout.put>
</@layout.extends>

