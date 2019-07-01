<template>
<div>
    <div id="search-box">
        <div class="container">
            <div class="row no-gutters">
                <div class="col-lg-6">
                    <div id="search-form" class="row no-gutters">
                        <div class="col-lg-12 col-12">
                            <h1 class="box-title">Antalya'yı keşfet</h1>
                            <p class="box-desc">Nerede, ne zaman, ne var?</p>
                            <VueCtkDateTimePicker  
                            v-model="datepicker"
                            :format="'YYYY-MM-DD'"
                            :formatted="LL"
                            :custom-shortcuts="customShortcuts"
                            :range="true" />
                        </div>
                        <div class="col-lg-12 col-12">
                            <multiselect v-model="types"
                                tag-placeholder="Add this as new tag"
                                placeholder="Search or add a tag"
                                label="name" track-by="code"
                                :options="options"
                                :multiple="true"
                                :taggable="true"
                                :max="3" 
                                @tag="addTag" />
                        </div>
                        <div class="col-lg-12 col-12">
                            <button class="btn btn-search">SEARCH</button>
                        </div>
                    </div>
                    <!-- #search-form -->
                </div>
                <!-- .col-lg-12 -->
            </div>
            <!-- .row -->  
        </div> 
    </div> 
    <!-- #search-box -->
    <div id="search-results-box" class="container">
        <div class="row">
            <div class="col-lg-12">
                <h2 class="font-size-14 pastel-blue">Etkinlikler</h2>
                <div class="days-menu desktop-hide">
                    <span id="event-day-prev">
                        <i class="fas fa-chevron-circle-left"></i>
                    </span>
                    <span>Bugün</span>
                    <span>Yarın</span>
                    <span>Ertesi</span>
                    <span id="event-day-next">
                        <i class="fas fa-chevron-circle-right"></i>
                    </span>
                </div>
                <div class="row">
                    <div v-for="eventDay in eventDays" class="events-downlist col-lg-4">
                        <h3>{{eventDay.day}}<span>{{eventDay.key.format("yyyy-MM-dd").format("dd.MM.yyyy")}}</span></h3>
                        <ul class="list">
                            <li v-for="event in eventDay.value">
                                <h4><a :href="event.url">{{event.name}}</a></h4>
                                <span :class="event.allDay ? 'badge event-begin allday' : 'badge event-begin'">{{event.startTime}}</span>
                                <span v-if="event.duration>0" class="duration"><i class="fas fa-hourglass-half"></i>&nbsp;{{event.duration}} min</span>
                                <div class="line-1">
                                    <span>{{events.type}}</span>
                                </div>
                                <div class="line-end">
                                    <div class="info">
                                        <div class="place">
                                            <i class="fas fa-map-marker-alt"></i><a :href="event.place.url">{{event.place.name}}</a>
                                        </div>
                                        <div class="attendee">
                                            <i class="fas fa-walking"></i><span>0</span>
                                        </div>
                                        <div class="comment">
                                            <i class="fas fa-comments"></i><span>0</span>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- .row -->
            </div>
        </div>
        <!-- .row -->
    </div>
    <!-- #search-results-box.container -->
</div>      
</template>
<script>

import {LocalDate} from '../../js/mydate';
import axiosApi from 'axios';
import VueCtkDateTimePicker from 'vue-ctk-date-time-picker';
import 'vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css';
import 'vue-multiselect/dist/vue-multiselect.min.css';
import Multiselect from 'vue-multiselect'

function makeEventTypeList(types) {
    return types.map(t=>t.code).join(',');
}
function getEvents(self) {           
              axiosApi.get('/events/json/map', {
                  params: {
                   startDate : self.datepicker.start,
                   endDate: self.datepicker.end,
                   language: self.language,
                   types: makeEventTypeList(self.types)
                  }
                })
                .then(function (response) {
                  if (response.data) {
                      console.log(response.data);
                    var eventsMap = response.data;
                    var currentDate = self.datepicker.start.format("yyyy-MM-dd");
                    self.eventDays = [];
                        while (currentDate <= self.datepicker.end.format("yyyy-MM-dd")) {
                            var eventKey = currentDate.format("yyyy-MM-dd");
                            var tempMap = {
                                key: eventKey,
                                day: currentDate.format("EEEE"),
                                value: (eventsMap[eventKey] !== undefined) ? eventsMap[eventKey] : []
                            }
                            console.log(tempMap);
                            self.eventDays.push(tempMap);
                            
                            currentDate = currentDate.addDays(1);
                        }
                  }
                })
                .catch(function (error) {
                  console.log(error);
                })
                .then(function () {
                  // always executed
                });  
}

export default {
  name: 'VueEventDays',
  data () {
    return {
    datepicker: '',
    eventDays: [],
    customShortcuts: [
        { label: 'Tomorrow', value: '+day', isSelected: false },
          { label: 'This Week', value: 'week', isSelected: true },
          { label: 'Next Week', value: '+week', isSelected: false },
          { label: 'This Month', value: 'month', isSelected: false },
          { label: 'Last Month', value: '-month', isSelected: false },
          { label: 'This Month', value: 'year', isSelected: false },
          { label: 'Weekend', value: 'weekend', isSelected: false }
        ],
    
    types: [
        { name: localeMessages['events.type.CONCERT'], code: '2' },
        { name: localeMessages['events.type.DANCE_AND_BALLET'], code: '3' },
        { name: localeMessages['events.type.VISITOR_ATTRACTIONS'], code: '14' }
      ],
      options: [
           { name: localeMessages['events.type.CONCERT'], code: '2'},
           { name: localeMessages['events.type.DANCE_AND_BALLET'], code: '3'},
          {  name: localeMessages['events.type.OPERA'], code: '4'},
          {  name: localeMessages['events.type.MUSICALS'], code: '5'},
           { name: localeMessages['events.type.COMEDY'], code: '6'},
           { name: localeMessages['events.type.DRAMA'], code: '7'},
           { name: localeMessages['events.type.ATHLETICS'], code: '8'},
           { name: localeMessages['events.type.BASKETBALL'], code: '9'}, 
           { name: localeMessages['events.type.GOLF'], code: '10'},
           { name: localeMessages['events.type.SOCCER'], code: '11'},
           { name: localeMessages['events.type.EXHIBITIONS'], code: '12'},
           { name: localeMessages['events.type.FESTIVALS'], code: '13'},  
           { name: localeMessages['events.type.VISITOR_ATTRACTIONS'], code: '14'},
           { name: localeMessages['events.type.TRANSPORT'], code: '15'},
           { name: localeMessages['events.type.MUSEUMS'], code: '16'},
           { name: localeMessages['events.type.FAMILY_SHOWS'], code: '17'},
           { name: localeMessages['events.type.SHOPPING'], code: '18'},                                   
           { name: localeMessages['events.type.DISCOUNTS'], code: '19'},                                    
           { name: localeMessages['events.type.EXCURSIONS'], code: '20'}                            
      ]
      }
  },
  components: {
      VueCtkDateTimePicker,
      Multiselect
  }, 
    computed : {
        language : function () {
            return document.getElementsByTagName("html")[0].getAttribute("lang");
        }
    },
    watch : {
        datepicker: function (val) {
            getEvents(this);
        },
        types: function (val) {
            getEvents(this);
        }
    },
    mounted () {
         getEvents(this);
    }


  }

</script>
