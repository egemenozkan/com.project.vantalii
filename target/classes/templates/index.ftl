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
<html>
<head>
	<#include '*/common/styles.ftl'>
    <script type="text/javascript">
        var popularPlaces = ${ popularPlaces };

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
                        <div class="row align-items-center justify-content-center">
                            <div id="app1" class="col-lg-12">
                                <search-form></search-form>
                            </div>
                        </div>
                    </div>
                </header>
                <!-- .v-detail_top -->
                <div class="v-body">
                    <div class="container">
                        <div class="row mx--1 v-categories">
                            <div class="col-lg-12">
                                <div class="v-categories_header">
                                    <h2 class="widget-title">
                                        <span>Featured Categories</span>
                                    </h2>
                                    <div class="description">
                                        What do you need to find? </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="v-category shops">
                                    <h4 class="category-info-title"> Nightlife </h4>
                                    <span class="category-number">3 Listings</span>
                                </div>
                            </div>
                            <div class="col-lg-8">
                                <div class="v-category shops">
                                    <h4 class="category-info-title"> Nightlife </h4>
                                    <span class="category-number">3 Listings</span>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="v-category shops">
                                    <h4 class="category-info-title"> Nightlife </h4>
                                    <span class="category-number">3 Listings</span>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="v-category shops">
                                    <h4 class="category-info-title"> Nightlife </h4>
                                    <span class="category-number">3 Listings</span>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="v-category shops">
                                    <h4 class="category-info-title"> Nightlife </h4>
                                    <span class="category-number">3 Listings</span>
                                </div>
                            </div>
                        </div>
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