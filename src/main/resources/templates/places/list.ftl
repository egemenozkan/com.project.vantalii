<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
<!-- Page Properties -->
<#assign title> 
    <#if mainType??>
        <@spring.message "places.mainType.${ mainType }" />
    <#else>
        <@spring.message "places.type.${ type }" />
    </#if>  
</#assign>
<#assign description> <@spring.message "page.home.description" /></#assign>
<#assign product="place">
<#assign category="list">
<#assign page="list">
<#assign styles=[]>
<#assign javascripts=[]>
<#assign  bundle="place_list"> 
<!DOCTYPE html>
<html lang="${ webPage.language.code?lower_case }" class="page places" data-language="${ webPage.language }" data-user-id="<#if (user?? &&  user.id??)>${ user.id?c! }<#else>0</#if>" data-place-id="0">
<head>
    <title>- Vantalii.com</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" crossorigin="anonymous">

    <link type="text/css" href="/static/assets/lightbox/css/lightgallery.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"
        integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
        crossorigin="" />
    <!-- Make sure you put this AFTER Leaflet's CSS -->
    <#include '*/common/styles.ftl'>
    <script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"
        integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og=="
        crossorigin=""></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
    </script>
    <script type="text/javascript" src="/static/assets/lightbox/js/lightgallery.min.js"></script>
    <script type="text/javascript">
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
                        <div class="row align-items-center justify-content-center">
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
                        <div class="row mx--1">
                            <div class="col-lg-8">
                                <#list pages as page>
                                    <div class="v-box v-box-list">
                                        <main class="">
                                            <div class="v-box_thumbnail">
                                                <figure>
                                                    <img src="https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=370"
                                                        width="370" />
                                                </figure>
                                            </div>
                                            <div class="v-box_list_detail">
                                                <div class="content">
                                                    <h3><a href="${ webPage.baseUrl! }/places/${ page.slug! }" class="stretched-link">${ page.title! }</a></h3>
                                                    <div class="meta">
                                                        <div class="location">
                                                            <i class="material-icons">
                                                                beenhere
                                                            </i>
                                                            <span>${ page.place.address.city! } ${ page.place.address.district?has_content?string(", " +  page.place.address.district!,"") }  ${ page.place.address.region?has_content?string(", " +  page.place.address.region!,"") }</span>
                                                        </div>
                                                        <#if page.place.contact?has_content && page.place.contact.phone?has_content>
                                                            <div class="number">
                                                                <i class="material-icons">
                                                                    phone
                                                                </i><span>${ page.place.contact.phone! }</span>
                                                            </div>
                                                        </#if>
                                                    </div>
                                                    <div class="description">
                                                        ${ page.description! }

                                                    </div>

                                                </div>
                                                <div class="footer">
                                                    <div class="category"><a href="${ webPage.baseUrl! }/places/m/${ page.place.type.mainType.slug }"><@spring.message "places.mainType.${ page.place.type.mainType! }" /></a>, <a href="${ webPage.baseUrl! }/places/t/${ page.place.type.slug }"><@spring.message "places.type.${ page.place.type! }" /></a></div>
                                                    <div class="status <#if !page.place.open>closed</#if>">
                                                        <#if page.place.open>
                                                            Açık
                                                        <#else>
                                                            Kapalı
                                                        </#if>
                                                    </div>
                                                </div>
                                            </div>
                                        </main>
                                    </div>
                                </#list>
                            </div>
                            <div class="col-lg-4">

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