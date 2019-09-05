<#ftl encoding="utf-8">

	<footer class="pt-4  py-md-3 border-top bg-light">
		<div class="container">
			<div class="row">
				<div class="col-12 col-md-12">
					<small class="d-block mb-3 text-muted">© 2019</small>
				</div>
				<div class="col-6 col-md">
					<h5></h5>
					<ul class="list-unstyled text-small">
						<#list placeMainTypes! as placeMainType><#if placeMainType != 'ALL' && placeMainType != 'NOTSET' >
						  <li><a class="text-muted" href="${ webPage.baseUrl! }/places/m/${ placeMainType.slug }"><@spring.message "places.mainType.${ placeMainType }" /></a></li></#if>
						</#list>
					</ul>
				</div>
			</div>
		</div>
	</footer>
 <#include '*/modals/modalSignIn.ftl'>