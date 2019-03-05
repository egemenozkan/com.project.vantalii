<#macro localDateTime value pattern><#assign parsedValues = ''><#if (value)??><#assign parsedDate = value?date("yyyy-MM-dd'T'HH:mm")><#assign parsedValues = parsedDate?string[pattern]></#if>${parsedValues}</#macro>
<#-- 21.04.2018 08:25" -->
<#macro localDateTimeTR value pattern><#assign parsedValues = ''><#if (value)??><#assign parsedDate = value?date("dd.MM.yyyy' 'HH:mm")><#assign parsedValues = parsedDate?string[pattern]></#if>${parsedValues}</#macro>

<#macro localDate value pattern><#assign parsedValues = ''><#if (value)??><#assign parsedDate = value?date("yyyy-MM-dd")><#assign parsedValues = parsedDate?string[pattern]></#if>${parsedValues}</#macro>

<#macro localTime value pattern><#assign parsedValues = ''><#if (value)??><#assign parsedDate = value?time("HH:mm")><#assign parsedValues = parsedDate?string[pattern]></#if>${parsedValues}</#macro>