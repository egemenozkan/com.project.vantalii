<#assign facebookConnectUrl>https://www.facebook.com/v3.2/dialog/oauth?client_id=370747544559370&redirect_uri=${ webPage.baseUrl! }/login/facebook&response_type=code</#assign>
<#assign vkConnectUrl>https://oauth.vk.com/authorize?client_id=6954043&display=page&redirect_uri=${ webPage.baseUrl! }/login/vkontakte?referer=${ webPage.canonical! }&scope=friends,email,account&response_type=code&v=5.95</#assign>

<!-- Modal -->
<div class="modal fade" id="modal-signIn" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <ul class="mx-auto">
            <li class="my-1"><a href="${ facebookConnectUrl }" class="btn btn-facebook"><i class="fab fa-facebook-f p-1"></i><span>Facebook</span></a></li>
            <li class="mb-1"><a href="${ vkConnectUrl }" class="btn btn-vkontakte"><i class="fab fa-vk p-1"></i><span>Vkontakte</span></a></li>
            <li><div id="btn-google"></div></li>
        </ul>          
      </div>
    </div>
  </div>
</div>