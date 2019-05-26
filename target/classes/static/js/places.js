import '../js/common';
import Vue from 'vue';
import VueComment from '../vue/VueComment.vue'
import VuePageGallery from '../vue/VuePageGallery.vue'

import {} from '../js/mydate';



Vue.config.productionTip = false

Vue.mixin({
    data: function() {
      return {
        m: localeMessages
      }
    }
  })

/* eslint-disable no-new */
const app1 = new Vue({
  el: '#app1',
  components: { 'vue-page-gallery': VuePageGallery}
})

const app2 = new Vue({
    el: '#app2',
    components: { 'vue-comments': VueComment }
  })
