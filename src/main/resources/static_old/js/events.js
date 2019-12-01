import '../js/common';
import Vue from 'vue';
import VueComment from '../vue/components/VueEventComment.vue'
import VueGallery from '../vue/components/VueEventGallery.vue'

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
const appGallery = new Vue({
  el: '#appGallery',
  components: { 'vue-gallery': VueGallery}
})

const appComments = new Vue({
    el: '#appComments',
    components: { 'vue-comments': VueComment }
  })
