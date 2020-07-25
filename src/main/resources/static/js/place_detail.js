import '../js/common';
import Vue from 'vue';
import VueComment from '../vue/components/VuePlaceComment.vue'
import VueGallery from '../vue/components/VuePlaceGallery.vue'

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


var mapInitialized = false;
function initMap() {
    var coordinates = $("address").data("coords");
    var mymap = L.map('mapid').setView(coordinates, 13);
    var marker = L.marker(coordinates).addTo(mymap);
    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
        attribution: 'OpenSteet Map',
        maxZoom: 18,
        id: 'mapbox.streets',
        accessToken: 'pk.eyJ1Ijoic2V2YWlzIiwiYSI6ImNrMzJyZGhzazBqNmMzbHA5ZGF0djZxYmMifQ.8JKxkenVc8a_TobZ9JZ7MQ'
    }).addTo(mymap);
    mapInitialized = true;
}
$(function () {
    $("#btn-toggle-map").click(
        function () {
            $(".v-map").toggle();
            if (!mapInitialized) {
                initMap();
            }
    
            if ($(".v-map").is(":visible")) {
                   $('html, body').animate({
                scrollTop: $(".v-map").offset().top - 100
            }, 2000);
            }
        }
    );


})