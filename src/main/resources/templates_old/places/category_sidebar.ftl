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
<@layout.extends name="layouts/placesHomepage.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
	<div id="" class="row mt-3">
        <div class="col-lg-3">
            <div class="box">
                <div class="box-body">
                    <ul>
                        <#list placeMainTypes as placeMainType>
                            <#if placeMainType != 'ALL' && placeMainType != 'NOTSET'>
                                <li><a class="text-muted" href="${ webPage.baseUrl! }/places/m/${ placeMainType?replace("_", "-")?lower_case }"><@spring.message "places.mainType.${ placeMainType }" /></a></li>
                                <li>
                                    <ul>
                                        <#list placeTypes as placeType> 
                                            <#if placeType.mainType! == placeMainType! && placeType != 'ALL' && placeMainType != 'NOTSET'>
                                                <li><a class="text-muted" href="${ webPage.baseUrl! }/places/t/${ placeType?replace("_", "-")?lower_case }"><@spring.message "places.type.${ placeType }" /></a></li>
                                            </#if>
                                        </#list>
                                    </ul>
                                </li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </div>     
        </div>
        <div class="col-lg-9">
            <div class="row places-cards">
                <#list pages as page>
                    <div class="col-4">
                        <div class="card">
                            <img class="card-img-top" src="/static/img/${ page.place.type }.jpg" alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title"><a href="${ webPage.baseUrl! }/places/${ page.slug }" class="stretched-link">${ page.title }</a></h5>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Cras justo odio</li>
                                <li class="list-group-item">Dapibus ac facilisis in</li>
                                <li class="list-group-item">Vestibulum at eros</li>
                            </ul>
                            <div class="card-body">
                                <a href="#" class="card-link">Card link</a>
                                <a href="#" class="card-link">Another link</a>
                            </div>
                             <div class="card-footer text-muted">
                                2 days ago
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

