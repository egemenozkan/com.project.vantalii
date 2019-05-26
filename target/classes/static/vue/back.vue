<div class="container">
    <div class="large-12 medium-12 small-12 cell">
      <label>Files
        <input type="file" id="files" ref="files" accept="image/*"  v-on:change="handleFilesUpload()"/>
      </label>
    </div>
    <progress max="100" :value.prop="uploadPercentage"></progress>
    <div class="large-12 medium-12 small-12 cell">
      <div class="grid-x">
        <div v-for="(file, key) in files" class="large-4 medium-4 small-6 cell file-listing">
          {{ file.name }}
          <img class="preview" v-bind:ref="'image'+parseInt( key )"/>
        </div>
      </div>
    </div>
    <br>
  <!--  <div class="large-12 medium-12 small-12 cell clear">
      <button v-on:click="addFiles()">Add Files</button>
    </div> -->
    <br>
    <div class="large-12 medium-12 small-12 cell">
      <button v-on:click="submitFiles()">Submit</button>
    </div>
  </div>