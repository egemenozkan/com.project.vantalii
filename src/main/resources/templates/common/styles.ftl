<!-- Bundles -->
<link rel="stylesheet" href="<@utils.staticUrl source="/bundle/css/${ bundle!'common' }.css" />">

<!-- Dynamic Styles Calls -->
<#list styles as style>
    <link rel="stylesheet" href="<@utils.staticUrl source="/bundle/css/${ style!'common' }.css" />">
</#list>