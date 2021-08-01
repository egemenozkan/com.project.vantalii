<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
<!-- Page Properties -->
<#assign title> <@spring.message "page.home.title" /> </#assign>
<#assign description> <@spring.message "page.home.description" /></#assign>
<#assign V_PRODUCT="main">
<#assign V_CATEGORY="homepage">
<#assign V_PAGE="index">
<#assign styles=[]>
<#assign javascripts=[]>
<#assign  bundle="index"> 

<!DOCTYPE html>
<html lang="${ webPage.language.code?lower_case }" class="page" data-language="${ webPage.language.code?lower_case }" data-user-id="<#if (user?? &&  user.id??)>${ user.id?c! }<#else>0</#if>" data-place-id="0">
<head>
    <title>${ title } - Vantalii</title>
    <#include "*/common/head.ftl"/>
	<#include '*/common/styles.ftl'/>
    <script type="text/javascript">
        var popularPlaces = ${ popularPlaces!'{}' };
        var popularEvents = ${ popularEvents!'{}' };
    </script>
</head>
<body>
    <div class="page-wrap">
        <#include '*/layout/header.ftl'>
        <div class="v-home">
            <div class="v-content">
                <header>
                    <div class="v-hero_img"></div>
                    <div class="v-overlay"></div>
                    <div class="container">
                        <div class="row align-items-center justify-content-center pt-searchForm">
                            <div id="app1" class="col-lg-12">
                                <search-form></search-form>
                            </div>
                        </div>
                    </div>
                </header>
                <!-- .v-detail_top -->
                <div class="v-body">
                    <div class="container">
                        
                    </div>
                </div>
                <!-- .v-body -->
            </div>
            <!-- .v-content -->
        </div>
        <!-- .v-page -->
		<#include '*/layout/footer.ftl'>
    </div>
    <!-- end of .page-wrap -->
    <#include '*/common/javascripts.ftl'>
    <#include '*/modals/modalSignIn.ftl'>
</body>

</html>