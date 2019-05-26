<#ftl encoding="utf-8">

<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->
<#assign title>
    <@spring.message "page.home.title" />
</#assign>
<#assign description>
    <@spring.message "page.home.description" />
</#assign>
<#assign category = "home">
<#assign page = "index">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "index">
<@layout.extends name="layouts/base.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
	    <div id="app1">
	    	<vue-event-days></vue-event-days>
	    </div>
        <!-- /.container -->
    </@layout.put>
    <@layout.put block="footer">
      <div class="container-fluid bottom-div">
        <div class="container">
            <div class="row ">
                <div class="col-lg-6 pagelink-div">
                     <ul>
                    <#list pages! as page>
                        <li><a href="${ webPage.baseUrl! }/places/${ page.slug }">${ page.title }</a></li>
                    </#list>
                    </ul>
                </div>
                
                <div class="col-lg-6">
                
                </div>
            </div>
        </div>
    </div>   
    </@layout.put>
</@layout.extends>

