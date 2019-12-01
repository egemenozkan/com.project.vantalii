<style>
input[type="file"] {
  position: absolute;
  top: -500px;
}
div.file-listing img {
  max-width: 90%;
}
</style>

<template>
    <div class="gallery">
      <gallery :images="images" :index="index" @close="index = null"></gallery>
      <div class="images-by-users">
        <span v-for="(image,imageIndex) in images"   :key="imageIndex"
      @click="index = imageIndex" :style="{ backgroundImage: 'url(' + image + ');'}"></span>
        <span style="background: transparent; text-align: center; vertical-align: middle;">
          <div v-if="online" class="upload-btn-wrapper">
            <button type="button">
              <i class="fa fa-camera"></i>
            </button>
            <input
              type="file"
              id="files"
              ref="files"
              accept="image/*"
              v-on:change="handleFilesUpload()"
            >
          </div>
          <div v-if="!online" class="upload-btn-wrapper">
            <button type="button" data-toggle="modal" data-target="#modal-signIn">
              <i class="fa fa-camera"></i>
            </button>
          </div>
        </span>
      </div>
      <modalAddPhoto :files="files" :message="message" :btnSubmit="submitFiles" :uploadPercentage="uploadPercentage"></modalAddPhoto>
    </div>
</template>

<script>
import axiosApi from "axios";
import Vue from "vue";
import VueGallery from 'vue-gallery';



Vue.component("modalAddPhoto", {
  props: ["files", "uploadPercentage", "btnSubmit", "message"],
  template: `<div class="modal fade" id="modal-addPhoto" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-body">
             <div class="progress">
                <div class="progress-bar" role="progressbar" :style="{ width: uploadPercentage + '%' }" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">{{ uploadPercentage }}%</div>
            </div> 
            <div class="large-12 medium-12 small-12 cell">
              <div class="grid-x">
                <div
                  v-for="(file, key) in files"
                  class="large-4 medium-4 small-6 cell file-listing"
                >
                  {{ file.name }}
                <img class="preview" v-bind:ref="'image'+parseInt( key )"/>
                </div>
                <div>{{ message }}</div>
                <button class="btn btn-success btn-sm" v-on:click="submitFilesX()">Submit</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>`,
  methods: {
    // Define the method that emits data to the parent as the first parameter to `$emit()`.
    // This is referenced in the <template> call in the parent. The second parameter is the payload.
    submitFilesX() {
      console.log("X");
      this.btnSubmit();
    }
  }
});

export default {
  /*
      Defines the data used by the component
    */
  data() {
    return {
      images: [],
      online: false,
      image: {
        background: ""
      },
      files: [],
      uploadPercentage: 0,
      message: 'Mesaj',
      index: null
    };
  },
  /*
      Defines the method used by the component
    */
  methods: {
    getPageImages: function() {
      var self = this;
      const getUrl = "/places/" + self.id + "/files";
      axiosApi
        .get(getUrl, {
          params: {}
        })
        .then(function(response) {
          if (response.data) {
            self.images = [];
            var length = response.data.length;
            for (var i = 0; i < length; i++) {
              self.images.push({
                response.data[i].lgUrl
              });
            }
            console.log(self.images);
            for (var j = 7; j > length; j--) {
              self.images.push({ background: "" });
            }

            console.log(self.images);
          }
        })
        .catch(function(error) {
          console.log(error);
        })
        .then(function() {
          // always executed
        });
    },
    addFiles() {
      this.$refs.files.click();
    },

    /*
        Submits files to the server
      */
    submitFiles() {
      /*
          Initialize the form data
        */
      let formData = new FormData();

      /*
          Iteate over any file sent over appending the files
          to the form data.
        */
      /*  for( var i = 0; i < this.files.length; i++ ){
          let file = this.files[i];

          formData.append('files[' + i + ']', file);
        } */

      var self = this;
      this.file = this.$refs.files.files[0];
//      alert(this.file.size);
console.log(this.file.size);
       self.message  = "Size: " + this.file.size;
      formData.append("file", this.file);
      formData.append("userId", this.userId);
      formData.append("pageId", this.id);
      //   console.log(this.file);
      //   console.log(formData);
      axiosApi
        .post("/places/file-upload/single", formData, {
          headers: {
            "Content-Type": "multipart/form-data"
          },
           maxContentLength: 15000000,
           timeout: 30000,
          onUploadProgress: function(progressEvent) {
            this.uploadPercentage = parseInt(
              Math.round((progressEvent.loaded * 100) / progressEvent.total)
            );
          }.bind(this)
        })
        .then(function() {
          console.log("SUCCESS!!");
          $("#modal-addPhoto").modal("hide");
          self.files = [];
          self.uploadPercentage = 0;
          console.log("cleared");
           self.message  = "Yüklendi";
          self.getPageImages();
        })
        .catch(function(response) {
            alert(response);
            self.message  = "Hata";
          console.log("FAILURE!!");
        });
    },

    /*
        Handles the uploading of files
      */
    handleFilesUpload() {
      $("#modal-addPhoto").modal("show");
      var self = this;
      self.files = [];
      self.uploadPercentage = 0;
      /*
          Get the uploaded files from the input.
        */
      let uploadedFiles = this.$refs.files.files;

      /*
          Adds the uploaded file to the files array
        */
      for (var i = 0; i < uploadedFiles.length; i++) {
        this.files.push(uploadedFiles[i]);
      }

      /*
          Generate image previews for the uploaded files
        */
    },

    /*
        Gets the preview image for the file.
      */
    getImagePreviews() {
      /*
          Iterate over all of the files and generate an image preview for each one.
        */
      for (let i = 0; i < this.files.length; i++) {
        /*
            Ensure the file is an image file
          */
        if (/\.(jpe?g|png|gif)$/i.test(this.files[i].name)) {
          /*
              Create a new FileReader object
            */
          let reader = new FileReader();

          /*
              Add an event listener for when the file has been loaded
              to update the src on the file preview.
            */
      reader.addEventListener("load", function(){
        this.$refs['image'+parseInt( i )][0].src = reader.result;
      }.bind(this), false);

          /*
              Read the data for the file in through the reader. When it has
              been loaded, we listen to the event propagated and set the image
              src to what was loaded from the reader.
            */
          reader.readAsDataURL(this.files[i]);
        } else {
            alert("failed", this.files[i].name);
        }
      }
    }
  },
  computed: {
    now: function() {
      return Date.now();
    },
    language: function() {
      return document
        .getElementsByTagName("html")[0]
        .getAttribute("data-language");
    },
    id: function() {
      return document
        .getElementsByTagName("html")[0]
        .getAttribute("data-place-id");
    },
    userId: function() {
      return document
        .getElementsByTagName("html")[0]
        .getAttribute("data-user-id");
    }
  },
  mounted() {
    var self = this;
    if (self.userId > 0) {
      self.online = true;
    }
self.message = true;
    self.getPageImages();
    console.log("-->", self.images);
  },components: {
      'gallery': VueGallery
    }
};
</script>