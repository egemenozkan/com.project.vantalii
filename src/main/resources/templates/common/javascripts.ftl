<#import "*/imports/utils.ftl" as utils/>

<script>
 var localeMessages = JSON.parse('${ localeMessages }');
 console.log("localeMessages", localeMessages);
</script>

<!-- Bundles -->
<script src="<@utils.staticUrl source="/bundle/js/${ bundle!'common' }.js?v=${ commitId }" />"></script>


<!-- Dynamic Javascript Calls -->
<#list javascripts as javascript>
    <script src="<@utils.staticUrl source="/js/${ javascript }.js?v=${ commitId }" />"></script>
</#list>

