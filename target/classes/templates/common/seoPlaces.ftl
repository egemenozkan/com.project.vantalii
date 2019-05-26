    <div class="container-fluid bottom-div">
        <div class="container">
            <div class="row ">
                <div class="col-lg-6 pagelink-div clearfix">
                     <ul>
                    <#list seoPages! as page>
                        <li><a href="${ webPage.baseUrl! }/places/${ page.slug }">${ page.title }</a></li>
                    </#list>
                    </ul>
                </div>
                
                <div class="col-lg-6">
                
                </div>
            </div>
        </div>
    </div> 