<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
	<!-- Page Properties -->
<#assign title> <@spring.message "page.home.title" /> </#assign>
<#assign description> <@spring.message "page.home.description" /></#assign>
<#assign category="home">
<#assign page="index">
<#assign styles=[]>
<#assign javascripts=[]>
<#assign  bundle="index"> 


<@layout.extends name="layouts/base.ftl">
	<@layout.put block="header"> </@layout.put>
	<@layout.put block="contents">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
						Burası
				</div>
			</div>
		</div>
		<search-form />

	</@layout.put> 
	<@layout.put block="footer">
	</@layout.put>
</@layout.extends> 