<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring />
<#import "*/imports/formatter.ftl" as formatter />
<#import "*/imports/utils.ftl" as utils /> 

        <footer class="v-footer">
            <div class="container">
                <div class="row">
                    <div class="col-md-3">
                    	<h5><@spring.message "footer.brand" /></h5>
                    	<ul>
                    	    <li><a href="<@utils.url url="/about-vantalii" />"><@spring.message "footer.links.aboutus" /></a></li>
                    		<li><a href="<@utils.url url="/events" />"><@spring.message "footer.links.events" /></a></li>
                       		<li><a href="<@utils.url url="/places" />"><@spring.message "footer.links.places" /></a></li>  
                    	</ul>
                    </div>
                    <div class="col-md-3">
                    	<ul>
                    		<li></li>
                    		<li></li>
                    	</ul>
                    	
                    </div>
                    <div class="col-md-3"></div>
                    <div class="col-md-3"></div>
                </div>
                <div class="row">
                	<div class="col-lg-12 text-center">
                		<span class="">Copyrights © 2021</span>
                	</div>
                </div>
            </div>
        </footer>