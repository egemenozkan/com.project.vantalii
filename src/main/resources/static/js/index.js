import '../js/common';
import Vue from 'vue';
import VueEventDays from '../vue/components/VueEventDays.vue'
import {LocalDate} from '../js/mydate';




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
  components: { 'vue-event-days': VueEventDays}
})