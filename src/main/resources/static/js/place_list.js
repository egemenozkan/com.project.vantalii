import '../js/common';
import Vue from 'vue';
import VueSearchForm from '../vue/components/VueSearchForm.vue'

import {} from '../js/mydate';

console.error("called_ place-list");

Vue.config.productionTip = false

Vue.mixin({
    data: function() {
      return {
        m: localeMessages
      }
    }
  })
const app1 = new Vue({
  el: '#app1',
  components: { 'search-form': VueSearchForm}
})

