<#ftl encoding="utf-8">

<div>
	<footer class="pt-4 my-md-5 pt-md-5 border-top">
		<div class="container">
			<div class="row">
				<div class="col-12 col-md">
					<small class="d-block mb-3 text-muted">© 2019</small>
				</div>
				<div class="col-6 col-md">
					<h5></h5>
					<ul class="list-unstyled text-small">
						<#list placeMainTypes! as placeMainType><#if placeMainType != 'ALL' && placeMainType != 'NOTSET' >
						                      
						
						  <li><a class="text-muted" href="${ webPage.baseUrl! }/places/m/${ placeMainType?replace("_", "-")?lower_case }"><@spring.message "places.mainType.${ placeMainType }" /></a></li></#if>
						</#list>
					</ul>
				</div>
				<div class="col-6 col-md">
					<h5>Resources</h5>
					<ul class="list-unstyled text-small">
						<li><a class="text-muted" href="#">Resource</a></li>
						<li><a class="text-muted" href="#">Resource name</a></li>
						<li><a class="text-muted" href="#">Another resource</a></li>
						<li><a class="text-muted" href="#">Final resource</a></li>
					</ul>
				</div>
				<div class="col-6 col-md">
					<h5>About</h5>
					<ul class="list-unstyled text-small">
						<li><a class="text-muted" href="#">Team</a></li>
						<li><a class="text-muted" href="#">Locations</a></li>
						<li><a class="text-muted" href="#">Privacy</a></li>
						<li><a class="text-muted" href="#">Terms</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>
</div>
 <#include '*/modals/modalSignIn.ftl'>