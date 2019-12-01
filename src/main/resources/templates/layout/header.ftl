<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 

<header>
    <div class="logo">
        <a href="#">LOGO</a>
    </div>
    <div class="v-nav_wrapper">
        <div >
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
                        <button class="btn btn-language" type="button">Türkçe</button>
                </li>
            </ul>
        </nav>
    </div>
</header>