<div id="comments" class="row">
	   <input type="hidden" id="placeId" value="${ placeId }">
	<div class="col-12">
	    <comment-item  v-for="comment in comments" v-bind:comment="comment" v-bind:key="comment.id"></comment-item>
		<textarea v-model="comment.message" rows="5" cols="3"></textarea>
		<button v-on:click="addComment">Send</button>
	</div>
</div>





<script>
Vue.component('comment-item', {
    props: ['comment'],
    template: '<div class="comment-div">{{ comment.message }}</div>'
  })
    var commentsInitial = [ {
        id : 1,
        title : 'Başlık',
        message : 'Burası'
    },
    {
        id : 2,
        title : 'Başlık 2',
        message : 'Burası 2'
    }
    
    ];
/*
 * 
 */
    var commentsApp = new Vue({
        el : '#comments',
        data : {
            comments : [],
            comment: {
                message: ''
            }
        },
        methods: {
            addComment: function () {
                var newComment = {
                        id:0,
                        title: 'Yeni',
                        message: this.comment.message,
                        user: {
                            id: 4
                        },
                        language: 'RUSSIAN'
                        
                };
               var self = this;
                axios.post('/places/' + this.id + '/comments', newComment)
                  .then(function (response) {
                    if (response.data.success) {
                        self.comments = response.data.comments;
                    }
                  })
                  .catch(function (error) {
                    console.log(error);
                  })
                  .then(function () {
                    // always executed
                  });  
            }
        },
        computed: {
            now: function () {
              return Date.now()
            },
            id : function () {
                return document.getElementById("placeId").value;
            }
    
          },
          mounted () {
              var self = this;
              axios.get('/places/comments', {
                  params: {
                   id: self.id
                  }
                })
                .then(function (response) {
                  if (response.data.success) {
                      self.comments = response.data.comments;
                      console.log(self.comments);
                  }
                })
                .catch(function (error) {
                  console.log(error);
                })
                .then(function () {
                  // always executed
                });  
            }
          
    })
</script>