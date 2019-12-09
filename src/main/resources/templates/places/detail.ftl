<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
<!-- Page Properties -->
<#assign title>
    ${ page.title! }
</#assign>
<#assign description>${ page.description! }</#assign>
<#assign V_PRODUCT="place">
<#assign V_CATEGORY="detail">
<#assign V_PAGE="detail">
<#assign styles=[]>
<#assign javascripts=[]>
<#assign  bundle="place_detail"> 

<!DOCTYPE html>
<html lang="${ webPage.language.code?lower_case }" class="page places" data-language="${ webPage.language }" data-user-id="<#if (user?? &&  user.id??)>${ user.id?c! }<#else>0</#if>" data-place-id="${ page.place.id?c!0 }">
<head>
    <title>${title} - Vantalii.com</title>
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
</head>

<body>
    <div class="page-wrap">
        <#include '*/layout/header.ftl'>
           <div class="v-page">
            <div class="v-content">
                    <header>
                        <div class="v-hero_img"></div>
                        <div class="v-overlay"></div>
                    </header>
                    <div class="v-content_top">
                        <div class="container v-before relative">
                            <div class="left">
                                <div class="v-page_logo v-after">
                                    <div class="bg-cover"
                                        style="background-image: url(https://fastly.4sqi.net/img/user/88x88/414624660_Vv3vMNtS_ZNUiXAQfYgECkPwIa-WifDOrIIisMxKEKmDpWFhN1i00cN5bhu6yftgGsi3SuyoK.png);">
                                        <a href="#"></a>
                                    </div>
                                </div>
                                <div class="v-page_title">
                                    <h1>
                                        <span>${ page.place.name! }</span>
                                        <em class="opened">Açık</em>
                                    </h1>
                                    <span class="v-page_category"><a href="${ webPage.baseUrl! }/places/m/${ page.place.type.mainType.slug }"><@spring.message "places.mainType.${ page.place.type.mainType! }" /></a>, <a href="${ webPage.baseUrl! }/places/t/${ page.place.type.slug }"><@spring.message "places.type.${ page.place.type! }" /></span>
                                    <ul class="v-page_region list-inline">
							     	  	<#if page.place?has_content>
								          	<li><a href="${ webPage.baseUrl! }/">${ page.place.address.city! }</a></li> 
								        </#if>
										<#if page.place.address.district?has_content>
											<li><a href="${ webPage.baseUrl! }/">${ page.place.address.district! }</a></li>
										</#if>
										<#if page.place.address.region?has_content>
											<li><a href="${ webPage.baseUrl! }/">${ page.place.address.region! }</a></li>
										</#if>
                                    </ul>
                                </div>
                            </div>
                            <div class="right">
                                <div class="v-page_share v-after">
                                    <button class="btn"><i class="material-icons">favorite_border</i><span>Beğen</span></button>
                                    <button class="btn"><i class="material-icons">share</i><span>Paylaş</span></button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- .v-detail_top -->
                    <div id="v-sticky-header" class="v-content_nav v-before">
                        <div class="container px-0">
                            <nav>
                                <ul>
                                    <li class="active">
                                        <a href=""><i class="icon-add_a_photo"></i><span>Tanım</span></a>
                                    </li>
                                    <li>
                                        <a href=""><span>Tanım 2</span></a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    <!-- .v-page_nav-->
                    <div class="v-body">
                        <div class="container">
                            <div id="mapid" class="v-map"></div>
                            <div class="row mx--1">
                                <div class="col-lg-8">
                                    <div id="photos" class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title">
                                                    <span>Fotoğraflar</span></h2>
                                            </div>
                                        </header>
                                        <main>
                                            <div id="appGallery">
                                                <vue-gallery></vue-gallery>
                                            </div>


                                        </main>
                                        <footer></footer>
                                    </div>
                                    <#if page.contents?? >
                                    <div class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title"><span><@spring.message "overview" /></span></h2>
                                            </div>
                                        </header>
                                        <main>
                                            <#list page.contents! as content>
                                                ${ content.text! }
                                            </#list>
                                        </main>
                                        <footer></footer>
                                    </div>
                                    </#if>
                                    <div id="facilities" class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title">
                                                    <span>Olanaklar</span></h2>
                                            </div>
                                        </header>
                                        <main>
                                            <ul>
                                                <li>
                                                    <i class="material-icons">fastfood</i><span>Yeme&İçme</span>
                                                </li>
                                                <li>
                                                    <i class="material-icons">fastfood</i><span>Yeme&İçme</span>
                                                </li>
                                                <li>
                                                    <i class="material-icons">fastfood</i><span>Yeme&İçme</span>
                                                </li>
                                                <li>
                                                    <i class="material-icons">fastfood</i><span>Yeme&İçme</span>
                                                </li>
                                                <li>
                                                    <i class="material-icons">fastfood</i><span>Yeme&İçme</span>
                                                </li>
                                                <li>
                                                    <i class="material-icons">fastfood</i><span>Yeme&İçme</span>
                                                </li>
                                            </ul>

                                        </main>
                                        <footer></footer>
                                    </div>
                                    <div class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title">
                                                    <span>Etkinlikler</span></h2>
                                            </div>
                                        </header>
                                        <main class="v-box_content">
                                            <div class="v-box_content_header">
                                                <i class="material-icons">chevron_left</i>
                                                <span>Ocak</span>
                                                <i class="material-icons">chevron_right</i>
                                            </div>
                                            <div class="v-collapse">
                                                <div class="v-collapse_header">
                                                    <a>
                                                        <div class="inner">
                                                            <div class="date">25 Aralık 2019</div>
                                                            <div class="time">12:00</div>
                                                            <div class="title text-bold">
                                                                Mehmet Kuzuloğlu Söyleşisi
                                                            </div>
                                                        </div>
                                                        <i class="material-icons">expand_more</i>
                                                    </a>
                                                </div>
                                                <div class="v-collapse_body">
                                                    <div>
                                                        Lorem Ipsum
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="v-collapse">
                                                <div class="v-collapse_header">
                                                    <a>
                                                        <div class="inner">
                                                            <div class="date">
                                                                25 Aralık
                                                                2019</div>
                                                            <div class="time">
                                                                12:00</div>
                                                            <div class="title text-bold">
                                                                Mehmet
                                                                Kuzuloğlu
                                                                Söyleşisi
                                                            </div>
                                                        </div>
                                                        <i class="material-icons">expand_more</i>
                                                    </a>
                                                </div>
                                                <div class="v-collapse_body">
                                                    <div>
                                                        Lorem Ipsum
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="v-collapse">
                                                <div class="v-collapse_header">
                                                    <a>
                                                        <div class="inner">
                                                            <div class="date">
                                                                25
                                                                Aralık
                                                                2019
                                                            </div>
                                                            <div class="time">
                                                                12:00
                                                            </div>
                                                            <div class="title text-bold">
                                                                Mehmet
                                                                Kuzuloğlu
                                                                Söyleşisi
                                                            </div>
                                                        </div>
                                                        <i class="material-icons">expand_more</i>
                                                    </a>
                                                </div>
                                                <div class="v-collapse_body">
                                                    <div>
                                                        Lorem
                                                        Ipsum
                                                    </div>
                                                </div>
                                            </div>
                                        </main>
                                        <footer></footer>
                                    </div>

                                    <div class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title">
                                                    <span><@spring.message "comments" /></span>
                                                </h2>
                                            </div>
                                        </header>
                                        <main>
                                          	<div id="appComments">
								         		<vue-comments></vue-comments>
								         	</div>
                                        </main>
                                        <footer></footer>
                                    </div>
                                </div>
                                <!-- .v-detail_left -->
                                <div class="col-lg-4 px-0 v-detail_right">
                                    <div id="v-contact" class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title">
                                                    <span>İletişim Bilgileri</span>
                                                </h2>
                                            </div>
                                        </header>
                                        <main>
                                            <button id="btn-toggle-map" type="button"
                                                class="btn btn-action btn-block">Haritada Göster</button>
                                            <div>
                                                <address data-coords="[${ page.place.address.lat?c! }, ${ page.place.address.lng?c! }]">
                                                	${ page.place.address.address! } ${ page.place.address.postCode! }  ${ page.place.address.region! }, ${ page.place.address.district! }/${ page.place.address.city! }
                                                </address>
                                                <div>
                                                <#if page.place.contact??>
                                                    <ul class="list">
                                                    	<#if page.place.contact.phone?has_content><li> <i class="material-icons">call</i><a href="tel:${ page.place.contact.phone! }">${ page.place.contact.phone! }</a></li></#if>
			                     						<#if page.place.contact.whatsapp?has_content><li><i class="fab fa-whatsapp"></i><span>${ page.place.contact.whatsapp! }</span></li></#if>
			                      						<#if page.place.contact.email?has_content><li><i class="material-icons">email</i><a href="mailto:${ page.place.contact.email! }">${ page.place.contact.email! }</a></li></#if>
									                    <#if page.place.contact.callCenter?has_content><li><i class="fas fa-phone-square"></i><span>${ page.place.contact.callCenter! }</span></li></#if>
									                    <#if page.place.contact.web?has_content><li><i class="material-icons">web</i><a href="${ page.place.contact.web! }?utm_source=vantalii">${ page.place.contact.web! }</a></li></#if>
                                                    </ul>
												</#if>
                                                </div>
                                            </div>
                                        </main>
                                        <footer></footer>
                                    </div>
                                    <!-- #weather-box-->
                                    <div class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title">
                                                    <span>Hava Durumu</span>
                                                </h2>
                                            </div>
                                        </header>
                                        <main>
                                            <a class="weatherwidget-io"
                                                href="https://forecast7.com/tr/36d9030d71/antalya/" data-label_1=""
                                                data-label_2="" data-font="Helvetica" data-theme="blank"></a>
                                            <script>
                                                ! function (d, s, id) {
                                                    var js, fjs = d.getElementsByTagName(s)[0];
                                                    if (!d.getElementById(id)) {
                                                        js = d.createElement(s);
                                                        js.id = id;
                                                        js.src = 'https://weatherwidget.io/js/widget.min.js';
                                                        fjs.parentNode.insertBefore(js, fjs);
                                                    }
                                                }(document, 'script', 'weatherwidget-io-js');
                                            </script>
                                        </main>
                                        <footer></footer>
                                    </div>
                                    <!-- #contact-box-->
                                    <#if dailyWorkingHours?has_content>
                                    <div class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title">
                                                    <span>Çalışma Saatleri</span>
                                                </h2>
                                            </div>
                                        </header>
                                        <main>
                                            <table class="table">
                                                <thead>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Açılış</th>
                                                        <th>Kapanış</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <#list dailyWorkingHours as daily>
                                                    	<tr>
                                                    		<td><@spring.message "datepicker.day.${ daily.day }" /></td>
                                                    		<td>${ daily.begin }</td>
                                                    		<td>${ daily.end }</td>
                                                    	</tr>
                                                    </#list>
                                                </tbody>
                                            </table>
                                        </main>
                                        <footer></footer>
                                    </div>
                                    </#if>
                                    <!-- -->
                                    <div class="v-box">
                                        <header class="v-before v-after">
                                            <div>
                                                <h2 class="title">
                                                    <span>Yakındaki Yerler</span>
                                                </h2>
                                            </div>
                                        </header>
                                        <main></main>
                                        <footer></footer>
                                    </div>
                                    <!-- -->
                                </div>
                                <!-- .v-detail_right -->
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