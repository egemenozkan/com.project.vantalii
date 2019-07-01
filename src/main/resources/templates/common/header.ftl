	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	   <div class="container">
		  <a class="navbar-brand mr-auto mr-lg-0 logo" href="/"><@spring.message "logo.text" /></a>
		  <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		
		  <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
		    <ul class="navbar-nav ml-auto">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><@spring.message "languages.${ webPage.language }" /></a>
		        <div class="dropdown-menu" aria-labelledby="dropdown01">
		          <#if webPage.language != 'TURKISH'>  
		               <a class="dropdown-item" href="https://www.vantalii.com/tr/">Türkçe</a>
		          </#if>
		          <#if webPage.language != 'ENGLISH'> 
		               <a class="dropdown-item" href="https://www.vantalii.com/en/">English</a>
	              </#if>
	              <#if webPage.language != 'RUSSIAN'> 
		               <a class="dropdown-item" href="https://www.vantalii.ru/">Русский</a>
		          </#if>
		        </div>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="<@utils.url url="/places" />"><@spring.message "nav.a.places" /></a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="<@utils.url url="/events" />"><@spring.message "nav.a.events" /></a>
		      </li>
		      <li class="nav-item">
		        <#if !(user??)> 
		            <button type="button" class="btn btn-outline-primary btn-sm" data-toggle="modal" data-target="#modal-signIn"><@spring.message "nav.a.signin" /></button>
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
		    </ul>
		<!--     <form class="form-inline my-2 my-lg-0"> -->
		<!--       <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search"> -->
		<!--       <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button> -->
		<!--     </form> -->
		  </div>
	  </div>
	</nav> 