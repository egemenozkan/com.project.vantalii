import '../js/common';
import Vue from 'vue';
import VueSearchPlace from '../vue/VueSearchPlace.vue'

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
const appSearchPlace = new Vue({
  el: '#appSearchPlace',
  components: { 'vue-search-place': VueSearchPlace}
})