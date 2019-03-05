<!-- Bundles -->
<link rel="stylesheet" href="//static.vantalii.com/bundle/css/${ bundle!'common' }.css">

<!-- Dynamic Styles Calls -->
<#list styles as style>
    <link rel="stylesheet" href="//static.vantalii.com/bundle/css/${ style!'common' }.css">
</#list>