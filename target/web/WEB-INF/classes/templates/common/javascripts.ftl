<!-- Bundles -->
<script src="//static.vantalii.com/bundle/js/${ bundle!'common' }.js"></script>

<!-- Dynamic Javascript Calls -->
<#list javascripts as javascript>
    <script src="<@spring.url '/resources/js/${ javascript }.js'/>"></script>
</#list>