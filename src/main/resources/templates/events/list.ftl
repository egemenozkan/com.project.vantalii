<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 
<!-- Page Properties -->
<#assign title> 
    <@spring.message "events.type.${ eventRequest.type }" />
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
    <title>${ title } - Vantalii</title>
    <#include '*/common/styles.ftl'>
        <link rel="stylesheet" href="<@utils.staticUrl source="/css/event-list.css" />">

    <script type="text/javascript">
        var popularPlaces = ${ popularPlaces };
        var popularEvents = ${ popularEvents };
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
                        <div class="row">
                        <#list eventsMap as _key, events>
                            <div class="events-downlist col-lg-4">
                                <div class="event-wrapper">
                                    <h3><@formatter.localDate _key "EEEE" /><span><@formatter.localDate _key "dd.MM.yyyy" /></span></h3>
                                    <ul class="list">
                                        <#list events as event>
                                        <li>
                                            <header> 
                                                <h4><a href="${ event.url! }">${ event.name!"-" }</a></h4>

                                                <span class="${ (event.allDay?has_content)?then('event-begin allday', 'event-begin') }">
                                                        <i class="far fa-clock"></i> ${ event.startTime }
                                                </span>
                                                <#if event.duration !=0>
                                                    <span class="event-duration"><i class="far fa-hourglass-half"></i>&nbsp;${event.duration} min</span>
                                                 </#if>
                                                <span class="event-types"><@spring.message "events.type.${ event.type }" /> </span>
                                            </header>
                                           
                                            <div>
                                                
                                               
                                                
                                            </div>    
                                            
                                            <div class="line-end">
                                                <div class="info">
                                                    <div class="event-place">
                                                        <i class="far fa-map-marker-alt"></i><a href="${ event.place.url }">${event.place.name}</a>
                                                    </div>
                                                    <#--  <div class="attendee">
                                                        <i class="fas fa-walking"></i><span>0</span>
                                                    </div>
                                                    <div class="comment">
                                                        <i class="fas fa-comments"></i><span>0</span>
                                                    </div>  -->
                                                </div>
                                            </div>
                                        </li>
                                        </#list>
                                    </ul>
                                </div>
                            </div>
                        </#list>    
                        </div>
                        <div class="row mx--1">
                            <div class="col-lg-8">
                                <#--  <#list events as event>
                                    <div class="v-box v-box-list">
                                        <a href="/${ event.slug! }">${ event.id } ${ event.name!"-" } - ${ event.type! } - ${ event.startDate! }</a>
                                    </div>
                                </#list>  -->
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