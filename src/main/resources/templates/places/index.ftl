<#import "/imports/spring.ftl" as spring/>
  <!doctype html>
  <html class="places">
   <head>
     <title>Antalya'da Yerler | Vantalii.com</title>
     <meta name=”description” content=”” />
    <link rel="stylesheet" href="//static.vantalii.com/bundle/css/places.css">
</head>

<body>
   <div class="container">
    <div class="row">
        <div class="col-lg-12">
            <#assign url = http.request.url />
            ${url}
        </div>
        <div class="keywords">
           
        </div>
    </div>
   
   </div>
   <script src="//static.vantalii.com/bundle/js/places.js"></script>
   </body>
  </html>