<!-- Bundles -->
<link rel="stylesheet" href="<@utils.staticUrl source="/bundle/css/${ bundle!'common' }.css?v=${ commitId }" />">

<!-- Dynamic Styles Calls -->
<#list styles as style>
    <link rel="stylesheet" href="<@utils.staticUrl source="/bundle/css/${ style!'common' }.css?v=${ commitId }" />">
</#list>