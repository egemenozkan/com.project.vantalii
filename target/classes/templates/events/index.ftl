<#ftl encoding="utf-8">

<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->
<#assign title>
</#assign>
<#assign description></#assign>
<#assign category = "events">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "events">
<@layout.extends name="layouts/eventsHomepage.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
	<div id="" class="row">
        <div class="col-lg-3">
        </div>
        <div class="col-lg-9">
          <ul>
              <#list events! as event>
                  <li><a href="${ webPage.baseUrl! }/events/${ event.slug }">${ event.title }</a></li>
              </#list>
          </ul>
        </div>
    </div>
    <!-- # -->
    </@layout.put>
    <@layout.put block="footer">
    </@layout.put>
</@layout.extends>

