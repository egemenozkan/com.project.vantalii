<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
 
 
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
                    <span class="input-group-addon"><i class="fa fa-sitemap" aria-hidden="true"></i></span>
                    <select class="form-control" name="user_type">
                        <option value="7">ADMIN</option>
<!--                         <option value="2">DRIVER</option> -->
<!--                         <option value="3">AGENCY</option> -->
                    </select>
                  </div>  
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
              </form>
			</div>
		</div>

		<br/>

		<h3>Sign in with</h3>

		<ul class="ax-item-group">
			<a class="ax-item" onclick="document.linkedin.submit();"><i class="axi axi-linkedin-square"></i> LinkedIn</a>
			<a class="ax-item" onclick="document.facebook.submit();"><i class="axi axi-facebook-square"></i> Facebook</a>
			<a class="ax-item" onclick="document.twitter.submit();"><i class="axi axi-twitter-square"></i> Twitter</a>
			<a class="ax-item" onclick="document.kakao.submit();"><i class="axi axi-ion-chatbubble"></i> Kakao</a>
			<a class="ax-item" onclick="document.github.submit();"><i class="axi axi-github-square"></i> Github</a>
		</ul>

		<div class="links">
			<a class="ax" href="https://www.axisj.com">https://www.axisj.com</a>, <a class="ax" href="https://github.com/axisj">https://github.com/axisj</a>
		</div>

		<!-- /.container -->
		<form action="/auth/facebook" name="facebook">
			<input type="hidden" name="scope" value="email,user_friends"/>
		</form>
		<form action="/auth/linkedin" name="linkedin">
		</form>
		<form action="/auth/twitter" name="twitter">
			<input type="hidden" name="scope" value="email"/>
		</form>
		<form action="/auth/kakao" name="kakao">
		</form>
		<form action="/auth/github" name="github">
			<input type="hidden" name="scope" value="email"/>
		</form>
	</@layout.put>
</@layout.extends>

