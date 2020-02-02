<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 

<header>
    <div class="logo">
        <a href="<@utils.url url="/" />"><img src="/static/img/logo.png"></a>
    </div>
    <div class="v-nav_wrapper">
        <div >
            <button class="btn btn-menu" type="button"><i class="material-icons">menu</i></button>
        </div>
        <nav id="v-nav_menu">
            <ul>
                <li>
                    <a href="<@utils.url url="/events" />"><@spring.message "nav.a.events" /></a>
                </li>
                <li>
                    <a href="<@utils.url url="/places" />"><@spring.message "nav.a.places" /></a>
                </li>
                <li class="v-nav_buttons">
                    <#if !(user??)> 
                        <button class="btn btn-white" type="button" data-toggle="modal" data-target="#modal-signIn"><@spring.message "nav.a.signin" /></button>
                    <#else>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="dropdown02" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${ user.firstName! } ${ user.lastName! }</a>
                            <div class="dropdown-menu" aria-labelledby="dropdown02">
                                <a class="nav-link" href="javascript:btnlogout.submit()"><@spring.message "nav.a.logout" /></a>
                            <form id="btnlogout" action="/logout" method="POST"> </form>
                            </div>
                        </li>
                    </#if>
                </li>
                <li>    
                    <div class="">
                            <button class="btn btn-language" type="button" class="nav-link dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false"><@spring.message "languages.${ webPage.language }" /></button>
                        <div class="dropdown-menu" aria-labelledby="dropdown01">
                            <#if webPage.language !='TURKISH'>
                                <a class="dropdown-item" href="https://www.vantalii.com/tr/">Türkçe</a>
                            </#if>
                            <#if webPage.language !='ENGLISH'>
                                <a class="dropdown-item" href="https://www.vantalii.com/en/">English</a>
                            </#if>
                            <#if webPage.language !='RUSSIAN'>
                                <a class="dropdown-item" href="https://www.vantalii.ru/">Русский</a>
                            </#if>
                        </div>
                    </div>
            </li>
            </ul>
        </nav>
    </div>
</header>