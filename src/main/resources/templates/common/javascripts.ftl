<#import "*/imports/utils.ftl" as utils/>

<script>
 var localeMessages = JSON.parse('${ localeMessages }');
 console.log("localeMessages", localeMessages);
</script>

<!-- Bundles -->
<script src="<@utils.staticUrl source="/bundle/js/${ bundle!'common' }.js" />"></script>


<!-- Dynamic Javascript Calls -->
<#list javascripts as javascript>
    <script src="<@utils.staticUrl source="/js/${ javascript }.js" />"></script>
</#list>

 <#if !(user??)> 
 <form id="googleForm" action="/login/google" method="get">
    <input type="hidden" name="idToken" />
 </form>
<script type="text/javascript">
  function onSuccess(googleUser) {
      var id_token = googleUser.getAuthResponse().id_token;
      if (id_token != null) {
          $('[name="idToken"]').val(id_token);
          $("#googleForm").submit();
      }
    }
  function onFailure(error) {
    console.log(error);
  }
  function renderButton() {
    gapi.signin2.render('btn-google', {
      'scope': 'profile email',
      'width': 200,
      'height': 40,
      'longtitle': true,
      'theme': 'dark',
      'onsuccess': onSuccess,
      'onfailure': onFailure
    });
  }
</script>
<script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
</#if>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-77382438-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-77382438-1');
</script>
