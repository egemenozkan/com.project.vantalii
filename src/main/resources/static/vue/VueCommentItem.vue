<template>
  <div id="comments" class="row">
      <input type="hidden" id="placeId" value="23">
    <div class="col-lg-12 col-12">
        <comment-item  v-for="comment in comments" v-bind:comment="comment" v-bind:key="comment.id"></comment-item>
    </div>
    <div class="col-lg-12 col-12">
    <textarea v-model="comment.message" rows="5"></textarea>
          <button class="" v-on:click="addComment">Send</button>
    </div>
                            <div class="new-comment">
                            <div class="header">
                                <input class="form-control" type="text" placeholder="Title" />
                            </div>
                            <div class="body">
                            <textarea class="form-control"  v-model="comment.message" placeholder="Message"></textarea>
                            </div>
                            <div class="footer">
                                <button class="btn btn-sm btn-comment-save btn-primary">Kaydet</button>
                            </div>
                        </div>   

  </div>
</template>

<script>

  Vue.component('comment-item', {
      props: ['comment'],
      template: '<div class="comment-div">{{ comment.message }}</div>'
    });
 

  export default {
    name: 'comments',
    data() {
      return {
        comments : [{
                        id:0,
                        title: 'Yeni',
                        message: 'ASTER',
                        user: {
                            id: 4
                        },
                        language: 'RUSSIAN'
                        
                }, {
                        id:1,
                        title: 'Yeni',
                        message: 'ASTER2',
                        user: {
                            id: 4
                        },
                        language: 'RUSSIAN'
                        
                }],
        comment: {
            message: 'HH'
        }
      }
    }
  }
  



</script>