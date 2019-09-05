<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->
<#assign title ="" />
<#assign description>
    <@spring.message "description.places.mainType.ALL"/>
</#assign>
<#assign category = "places">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "places_index">

<@layout.extends name="layouts/places/indexPlaces.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
           <div id="appSearchPlace">
                <vue-search-place></vue-search-place>
           </div>
           adasd
    </@layout.put>
    <@layout.put block="footer">
   <#--     <#include '*/common/seoPlaces.ftl'>   -->
    </@layout.put>
</@layout.extends>