<#macro valueLabel list>
    <#if list??>
        <#list list as elem>
            {value: '${ elem.id }', label: '${ elem.value }'}
            <#if elem_has_next>,</#if>
        </#list>
    </#if>
</#macro>
