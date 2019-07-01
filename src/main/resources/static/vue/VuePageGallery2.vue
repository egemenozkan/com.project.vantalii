<template>
            <div class="gallery">
	            <div class="images-by-users">
                  <span v-for="(image,index) in images" :style="image.background"></span>
                  <span style="background: transparent; text-align: center; vertical-align: middle;">
                    <div v-if="online" class="upload-btn-wrapper">
                      <button><i class="fa fa-camera"></i></button>
                      <input type="file" id="file" ref="file" v-on:change="addImage()" />
                    </div>
                    <div v-if="!online" class="upload-btn-wrapper">
                      <button type="button" data-toggle="modal" data-target="#modal-signIn"><i class="fa fa-camera"></i></button>
                    </div>
                  </span>
	            </div>
            </div>
</template>

<script>
/*
  Vue.component('comment-item', {
      props: ['comment'],
      template: '<div class="comment-div">{{ comment.message }}</div>'
    }); */
 
import axiosApi from 'axios';

  export default {
    name: 'images',
    data() {
      return {
        images : [],
        online : false,
        image: {
            background: ''
        },
        file: ''
      }
    },
    methods: {
            addImage: function () {
              var self = this;
               let formData = new FormData();
            this.file = this.$refs.file.files[0];
            formData.append('file', this.file);
            formData.append('userId', 15);
            formData.append('pageId', this.id);
            console.log(this.file);
            console.log(formData);
            axiosApi.post( '/places/file-upload/single',
              formData,
              {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
              }
            ).then(function(){
              self.getPageImages();
              console.log('SUCCESS!!');
            })
            .catch(function(){
              console.log('FAILURE!!');
            });
            return;
            },
            getPageImages: function () {
              var self = this;
               const getUrl = '/places/' + self.id + '/files';
             axiosApi.get(getUrl, {
                  params: {
                  }
                })
                .then(function (response) {
                  if (response.data) {        
                    self.images = [];              
                      var length = response.data.length;
                      for (var i = 0; i < length; i++) {
                        self.images.push({background: 'background-image: url(' + response.data[i].url + ');' });
                      }
                      
                      for (var j = 7; j > length; j--) {
                        self.images.push({background: ''});
                      }
                      
                      console.log(self.images);
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
              return document.getElementsByTagName("html")[0].getAttribute("data-place-id");
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
              self.getPageImages(); 
            }
  }
  



</script>