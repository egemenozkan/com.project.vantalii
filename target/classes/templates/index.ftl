<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
<!-- Page Properties -->
<#assign title> <@spring.message "page.home.title" /> </#assign>
<#assign description> <@spring.message "page.home.description" /></#assign>
<#assign product="main">
<#assign category="homepage">
<#assign page="index">
<#assign styles=[]>
<#assign javascripts=[]>
<#assign  bundle="index"> 

<!DOCTYPE html>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<#include '*/common/styles.ftl'>

    <link type="text/css" href="assets/lightbox/css/lightgallery.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"
        integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
        crossorigin="" />
    <!-- Make sure you put this AFTER Leaflet's CSS -->
    <script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"
        integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og=="
        crossorigin=""></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous">
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
    </script>
    <script type="text/javascript" src="assets/lightbox/js/lightgallery.min.js"></script>
    <script type="text/javascript" src="assets/js/common.js"></script>
</head>

<body>
    <div class="page-wrap">
        <header>
            <div class="logo">
                <a href="#">LOGO</a>
            </div>
            <div class="v-nav_wrapper">
                <div>
                    <button class="btn btn-menu" type="button"><i class="material-icons">menu</i></button>
                </div>
                <nav id="v-nav_menu">
                    <ul>
                        <li>
                            <a href="#">Etkinlikler</a>
                        </li>
                        <li>
                            <a href="#">Gezilecek Yerler</a>
                        </li>
                        <li>
                            <a href="#">Transferler</a>
                        </li>
                        <li class="v-nav_buttons">
                            <button class="btn btn-white" type="button">Giriş Yap</button>
                            <button class="btn btn-language" type="button">Türkçe</button>
                        </li>
                    </ul>
                </nav>
            </div>


        </header>
        <div class="v-home">
            <div class="v-content">
                <header>
                    <div class="v-hero_img"></div>
                    <div class="v-overlay"></div>
                    <div class="container">
                        <div class="row align-items-center justify-content-center">
                            <div class="col-lg-10">
                                <ul class="v-form_nav list">
                                    <li class="active"><i class="material-icons">near_me</i><button>Places</button></li>
                                    <li><i class="material-icons">local_activity</i><button>Events</button></li>
                                </ul>
                                <form class="v-form">
                                    <div class="v-form_inner place-search">
                                        <div class="search">
                                            <i class="material-icons">search</i>
                                            <input id="search" type="text" placeholder="What are you looking for?">
                                        </div>
                                        <div class="region">
                                            <i class="material-icons">near_me</i>
                                            <input id="region" type="text" placeholder="Nerede ?">
                                        </div>
                                        <div class="v-button">
                                            <button class="btn btn-submit">Ara</button>
                                        </div>
                                    </div>
                                </form>
                                <form class="v-form">
                                    <div class="v-form_inner event-search">
                                        <div class="search">
                                            <i class="material-icons">search</i>
                                            <input id="eventSearch" type="text" placeholder="What are you looking for?">
                                        </div>
                                        <div class="region">
                                            <i class="material-icons">near_me</i>
                                            <input id="eventRegion" type="text" placeholder="Nerede ?">
                                        </div>
                                        <div class="time">
                                            <i class="material-icons">access_time</i>
                                            <input id="eventTime" type="text" placeholder="Ne zaman ?">
                                        </div>
                                        <div class="v-button">
                                            <button class="btn btn-submit">Ara</button>
                                        </div>
                                    </div>
                                </form>
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
        <footer class="v-footer">
            <div class="container">
                <div class="row">
                    <div class="col-md-3">A</div>
                    <div class="col-md-3">B</div>
                    <div class="col-md-3">C</div>
                    <div class="col-md-3">D</div>
                </div>
            </div>
        </footer>
    </div>
    <!-- end of .page-wrap -->
    <script>
        // When the user scrolls the page, execute myFunction
        window.onscroll = function () {
            stickyHeader()
        };


        function stickyHeader() {
            // Get the header
            var header = document.getElementById("v-sticky-header");

            // Get the offset position of the navbar
            var sticky = header.offsetTop;

            if (window.pageYOffset > sticky) {
                header.classList.add("js-sticky");
            } else {
                header.classList.remove("js-sticky");
            }
        }
    </script>
    <script>
    </script>
</body>

</html>