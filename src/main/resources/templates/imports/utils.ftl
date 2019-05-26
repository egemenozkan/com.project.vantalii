<#ftl encoding="utf-8">

<#macro valueLabel list>
    <#if list??>
        <#list list as elem>
            {value: '${ elem.id }', label: '${ elem.value }'}
            <#if elem_has_next>,</#if>
        </#list>
    </#if>
</#macro>

<#macro staticUrl source>
<@compress single_line=true>
    <#if webPage.environment == 'prod'>
        //static.vantalii.com${ source }
    <#else>
       /static${ source }    
    </#if>
</@compress>    
</#macro>

<#macro url url>
<@compress single_line=true>
    <#if webPage.environment == 'prod'>
        ${ webPage.baseUrl }${ url }?env=prod
    <#else>
        ${ webPage.baseUrl }${ url }?env=dev
    </#if>
</@compress>    
</#macro>

<#macro address address>
<@compress single_line=true>
    <#if address??>
        <span class="address" data-coords="${ address.lat! }, ${ address.lng! }">
        <#if address.address??>
            ${ address.address! },
        </#if>
        <#if address.postCode??>
            ${ address.postCode! },
        </#if>
         <#if address.city??>
            ${ address.city! },
        </#if>
         <#if address.region??>
            ${ address.region! },
        </#if>
        <#if address.subregion??>
            ${ address.subregion! }
        </#if>
        </span>
    </#if>
</@compress>    
</#macro>