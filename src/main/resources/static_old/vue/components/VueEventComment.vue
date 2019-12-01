<template>
                    <div class="comments">
                        <div class="header">
                            <h4 class="">{{ m['comments']}}</h4>
                        </div>
                        <div v-if="online">
                          <div v-for="comment in comments" v-bind:comment="comment" v-bind:key="comment.id">
                            <div class="comment-item">
                                <div class="title">{{ comment.title }}</div>
                                <div class="text">{{ comment.message }}</div>
                                <span class="datetime">{{ comment.createDateTime.dateAsString() }}</span>
                                <span class="user">{{ comment.user.firstName }} {{ comment.user.lastName[0] }}.</span>
                            </div>
                          </div>
                          <div class="new-comment">
                              <div class="header">
                                  <input class="form-control" type="text" placeholder="Title" v-model="comment.title"/>
                              </div>
                              <div class="body">
                              <textarea class="form-control" placeholder="Message" v-model="comment.message"></textarea>
                              </div>
                              <div class="footer">
                                  <button class="btn btn-sm btn-comment-save btn-primary" v-on:click="addComment">Kaydet</button>
                              </div>
                          </div>
                        </div> 
                        <div v-else>
                              <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#modal-signIn">{{ m['comments']}}</button>
                        </div> 
                    </div>
<!--
  <div id="comments" class="row comment-div">
    <div class="col-lg-12 col-12">
	    <div v-for="comment in comments" v-bind:comment="comment" v-bind:key="comment.id">
	       <div class="comment-div">{{ comment.message }}</div>
	    </div>
    </div>
    <div class="col-lg-12 col-12">
    <textarea v-model="comment.message" rows="5"></textarea>
    <div>
      <button class="btn btn-save" v-on:click="addComment">Send</button>
    </div>
    </div>
  </div> -->
</template>

<script>
/*
  Vue.component('comment-item', {
      props: ['comment'],
      template: '<div class="comment-div">{{ comment.message }}</div>'
    }); */
 
import axiosApi from 'axios';

  export default {
    name: 'comments',
    data() {
      return {
        comments : [],
        online : false,
        comment: {
            message: ''
        }
      }
    },
    methods: {
            addComment: function () {
                var self = this;
                var newComment = {
                        id:0,
                        title: self.comment.title,
                        message: self.comment.message,
                        user: {
                            id: self.userId
                        },
                        language: self.language
                        
                };
                axiosApi.post('/events/' + this.id + '/comments', newComment)
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
            language : function () {
                return document.getElementsByTagName("html")[0].getAttribute("data-language");
            },
            id : function () {
              return document.getElementsByTagName("html")[0].getAttribute("data-event-id");
            },
            userId : function () {
              return document.getElementsByTagName("html")[0].getAttribute("data-user-id");
            }
    
          },
          mounted () {
              var self = this;
              if  (self.userId > 0) {
                self.online = true;
              }
              console.log(self.userId + "----userId-----");
              axiosApi.get('/places/comments', {
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
  }
  



</script>