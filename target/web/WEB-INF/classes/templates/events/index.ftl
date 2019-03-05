 <#import "/imports/spring.ftl" as spring/>
  <!doctype html>
  <html class="events">
   <head>
     <title>Antalya'da Etkinlikler | Vantalii.com</title>
     <meta name=”description” content=”” />
    <link rel="stylesheet" href="//static.vantalii.com/bundle/css/events.css">
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
   <script src="static/bundle/js/events.js"></script>
   </body>
  </html>