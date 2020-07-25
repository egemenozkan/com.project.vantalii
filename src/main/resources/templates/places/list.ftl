<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
<!-- Page Properties -->
<#assign title> 
        <@spring.message "places.type.${ type }" />
</#assign>
<#assign description> <@spring.message "description.places.type.${ type }" /></#assign>
<#assign product="place">
<#assign category="list">
<#assign page="list">
<#assign styles=[]>
<#assign javascripts=[]>
<#assign  bundle="place_list"> 
<!DOCTYPE html>
<html lang="${ webPage.language.code?lower_case }" class="page places" data-language="${ webPage.language }" data-user-id="<#if (user?? &&  user.id??)>${ user.id?c! }<#else>0</#if>" data-place-id="0">
<head>
    <title>${ title }- Vantalii</title>
    <meta name="description" content="${ description! }" />
    <link rel="canonical" href="${ webPage.canonical! }" />
    <link href="https://www.vantalii.com/tr/places/t/${ slug! }" hreflang="tr" rel="alternate">
    <link href="https://www.vantalii.com/en/places/t/${ slug! }" hreflang="en" rel="alternate">
    <link href="https://www.vantalii.ru/places/t/${ slug! }" hreflang="ru" rel="alternate">

    <#include '*/common/head.ftl'>
    <#include '*/common/styles.ftl'>
    <script type="text/javascript">
        var popularPlaces = ${ popularPlaces!'{}' };
        var popularEvents = ${ popularEvents!'{}' };
    </script>
</head>

<body>
    <div class="page-wrap">
        <#include '*/layout/header.ftl'>
        <div class="v-list">
            <div class="v-content">
                <header>
                    <div class="v-hero_img"></div>
                    <div class="v-overlay"></div>
                    <div class="container">
                        <div class="row align-items-center justify-content-center pt-searchForm">
                            <div class="col-lg-12">
                                <div id="app1">
                                    <search-form></search-form>
                                </div>
                                <h1>${ title }</h1>
                            </div>
                        </div>
                    </div>
                </header>

                <!-- .v-detail_top -->
                <div class="v-body">
                    <div class="container">
                        <div class="row">
                            <#list pages as page>
                                <div class="wil-col-5 col-md-3 col-sm-6 mb-3">
                                    <article class="list-item wil-shadow js-listing-module wil-shadow wil-flex-column-between">
                                        <div class="listing_firstWrap__36UOZ">
                                            <!---->
                                            <header class="listing-header is-verified">
                                               <span
                                                        class="wilcity-slider-gradient-before"></span>
                                                    <div class="listing_img__3pwlB pos-a-full bg-cover"
                                                        style="background-image:
                                                        url('https://www.vantalii.ru/static/img/categories/places/${ page.place.type! }.jpg');">
                                                        <img src="https://www.vantalii.ru/static/img/categories/places/${ page.place.type! }.jpg"
                                                            alt="Statue of Liberty"></div> <span
                                                        class="wilcity-slider-gradient-after"></span>
                                            </header>
                                            <div class="listing_body__31ndf"> <span
                                                    class="listing_goo__3r7Tj">
                                                     <i class="list-header-icon fad ${ page.place.type.icon }"></i>
                                                     </span>
                                                <h2 class="list-title text-ellipsis">
                                                    <a href="${ webPage.baseUrl! }/places/${ page.slug! }" class="stretched-link">${ page.title! }</a>
                                                </h2>
                                                <div class="listing_tagline__1cOB3 text-ellipsis">
                                                    ${ page.description! }
                                                </div>
                                                <div class="listing_meta__6BbCG"
                                                    style="position: relative;"
                                                        target="_blank" class="text-ellipsis w-100">
                                                            <i class="fad fa-map-marker"></i>
                                                            <span>${ page.place.address.city! } ${
                                                                page.place.address.district?has_content?string(", " +
                                                                page.place.address.district!,"") } ${
                                                                page.place.address.region?has_content?string(", " +
                                                                page.place.address.region!,"") }</span>
                                                        <#if page.place.contact?? && page.place.contact.phone?has_content>
                                                        <a href="tel: ${ page.place.contact.phone! }" target="_self" class="text-ellipsis w-100">
                                                            <i class="fad fa-phone"></i><span>${ page.place.contact.phone! }</span>
                                                        </a>
                                                        </#if>
                                                </div>
                                            </div>
                                        </div>
                                        <footer class="listing_footer__1PzMC">
                                            <div class="text-ellipsis">
                                                <div
                                                    class="icon-box-1_module__uyg5F text-ellipsis icon-box-1_style2__1EMOP">
                                                   <a href="${ webPage.baseUrl! }/places/m/${ page.place.type.mainType.slug }">
                                                       <@spring.message "places.mainType.${ page.place.type.mainType! }" />
                                                       </a>, <a
                                                       href="${ webPage.baseUrl! }/places/t/${ page.place.type.slug }">
                                                       <@spring.message "places.type.${ page.place.type! }" /></a>
                                                   
                                                </div>
                                            </div>
                                            <div class="listing_footerRight__2398w">
                                            </div>
                                        </footer>
                                    </article>
                                </div>
                                </#list>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- .v-body -->
            </div>
            <!-- .v-content -->
        </div>
        <!-- .v-list -->
  		<#include '*/layout/footer.ftl'>
    </div>
    <!-- end of .page-wrap -->
    <#include '*/common/javascripts.ftl'>
    <#include '*/modals/modalSignIn.ftl'>
</body>

</html>