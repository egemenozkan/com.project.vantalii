<#ftl encoding="utf-8">.
<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>
 
 
<!-- Page Properties -->
<#assign title>
    <@spring.message "page.home.title" />
</#assign>
<#assign description>
    <@spring.message "page.home.description" />
</#assign>
<#assign category = "home">
<#assign page = "index">
<#assign styles = []>
<#assign javascripts = []>
<#assign bundle = "index">
<@layout.extends name="layouts/base.ftl">
	<@layout.put block="contents">
		<div class="ax-panel good">
			<div class="ax-panel-heading">
				Login
			</div>
			<div class="ax-panel-body">
                <form action="<@spring.url '/perform_login' />" method='POST'>
                  <div class="input-group mb-3">
                    <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span>
                    <input class="form-control" name="username" placeholder="Enter your username" type="text">
                  </div>
                  <div class="input-group mb-4">
                    <span class="input-group-addon"><i class="fa fa-unlock-alt" aria-hidden="true"></i></span>
                    <input class="form-control" placeholder="Enter your password" type="password" name="password">
                  </div>
                  <div class="row">
                    <div class="col-6">
                     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                      <button type="submit" class="btn btn-primary px-4">Login</button>
                    </div>
                    <div class="col-6 text-right">
                      <button type="button" class="btn btn-link px-0">Forgot password?</button>
                    </div>
                  </div>
                  <a href="https://www.facebook.com/v3.2/dialog/oauth?client_id=287713494586438&response_type=code&redirect_uri=https://www.vantalii.com/login/facebook">Facebook</a>
                  <a href="https://oauth.vk.com/authorize?client_id=6942392&display=page&redirect_uri=https://www.vantalii.com/login/vkontakte&scope=friends,email&response_type=code&v=5.95">VKontakte</a>  
              </form>
			</div>
		</div>
		<!-- /.container -->
	</@layout.put>
</@layout.extends>

