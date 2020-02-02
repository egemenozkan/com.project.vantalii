<template>
    <div id="searchForm2">
        <ul class="v-form_nav list">
            <li :class="activeForm =='place' ? 'active' : ''" @click="changeForm('place')"><i class="far fa-map-marker-alt"></i><button>{{ m['searchForm.places'] }}</button></li>
            <li :class="activeForm =='event' ? 'active' : ''" @click="changeForm('event')"><i class="far fa-calendar"></i><button>{{ m['searchForm.events'] }}</button></li>
        </ul>
        <form class="v-form" v-if="activeForm == 'place'">
            <div class="v-form_inner place-search">
                <div class="search">
                    <i class="far fa-search"></i>
                    <autocomplete id="" :owner="place" :placeholder="m['searchForm.places.placeholder']" autocompleteUrl="/places/autocomplete" />
                </div>
                <div class="region">
                    <i class="far fa-location"></i>
                    <popularregions :owner="region" />
                </div>
                <div class="v-button">
                    <button type="button" class="btn btn-submit" @click="searchPlace">{{ m['searchForm.button.search'] }}</button>
                </div>
            </div>
        </form>
        <form class="v-form" v-if="activeForm == 'event'">
            <div class="v-form_inner event-search">
                <div class="search">
                    <i class="far fa-search"></i>
                    <autocomplete id="" :owner="event" :placeholder="m['searchForm.places.placeholder']" autocompleteUrl="/events/autocomplete" />
                </div>
                <div class="region">
                    <i class="far fa-location"></i>
                    <popularregions :owner="region" />
                </div>
                <div class="time">
                    <i class="far fa-clock"></i>
                    <VueCtkDateTimePicker v-model="dateTimePicker" :format="'YYYY-MM-DD'" :custom-shortcuts="customShortcuts" formatted="LL" label="Zaman" color="#c50143" :range="true" :first-day-of-week="1" :locale="language" />
                </div>
                <div class="v-button">
                    <button type="button" class="btn btn-submit" @click="searchEvent">{{ m['searchForm.button.search'] }}</button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
import axiosApi from 'axios';
import autocomplete from '../components/VueAutocomplete.vue';
import popularRegions from '../components/VuePopularRegion.vue';

import VueCtkDateTimePicker from 'vue-ctk-date-time-picker';
import 'vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css';
var moment = require('moment');
export default {
    name: 'searchForm',
    components: {
        'autocomplete': autocomplete,
        'popularregions': popularRegions,
        VueCtkDateTimePicker,

    },
    data() {
        return {
            activeForm: 'place',
            gis: {
                districts: []
            },
            place: {
                populars: popularPlaces,
                value: '',
                url: ''
            },
            region: {
                value: '',
                queryString: ''

            },
            event: {
                populars: popularEvents,
                value: '',
                url: ''
            },
            dateTimePicker: "",
            eventDays: [],
            customShortcuts: [
                /*
                datepicker.tomorrow=Yarın
datepicker.thisWeek=Bu hafta
datepicker.weekend=Haftasonu
datepicker.nextWeek=Gelecek hafta
datepicker.thisMonth=Bu ay
datepicker.nextMonth=Gelecek ay */
                {
                    key: 'today',
                    shortcut: 'today',
                    label: localeMessages['datepicker.today'],
                    value: 'day'
                },
                {
                    key: 'tomorrow',
                    shortcut: 'tomorrow',
                    label: localeMessages['datepicker.tomorrow'],
                    value: () => {
                        return {
                            start: moment().add(1, 'days'),
                            end: moment().add(1, 'days')
                        }
                    },
                    callback: ({ start, end }) => {}
                }, {
                    key: 'thisWeek',
                    shortcut: 'thisWeek',
                    label: localeMessages['datepicker.thisWeek'],
                    value: () => {
                        return {
                            start: moment(),
                            end: moment().day(7)
                        }
                    },
                    callback: ({ start, end }) => {}
                },
                {
                    key: 'weekend',
                    shortcut: 'weekend',
                    label: localeMessages['datepicker.weekend'],
                    value: () => {
                        return {
                            start: moment().day(5),
                            end: moment().day(7)
                        }
                    },
                    callback: ({ start, end }) => {}
                },
                {
                    key: 'thisMonth',
                    shortcut: 'thisMonth',
                    label: localeMessages['datepicker.thisMonth'],
                    value: () => {
                        return {
                            start: moment(),
                            end: moment().add(1, 'months').date(0)
                        }
                    },
                    callback: ({ start, end }) => {}
                },
                {
                    key: 'nextMonth',
                   
                    label: localeMessages['datepicker.nextMonth'],
                    value: () => {
                        return {
                            start: moment().add(1, 'months').date(1),
                            end: moment().add(2, 'months').date(0)
                        }
                    },
                    callback: ({ start, end }) => {}
                }
            ],
            types: [{
                    name: localeMessages['events.type.CONCERT'],
                    code: '2'
                },
                {
                    name: localeMessages['events.type.DANCE_AND_BALLET'],
                    code: '3'
                },
                {
                    name: localeMessages['events.type.VISITOR_ATTRACTIONS'],
                    code: '14'
                },
                {
                    name: localeMessages['events.type.SHOPPING'],
                    code: '18'
                },
                {
                    name: localeMessages['events.type.EXHIBITIONS'],
                    code: '12'
                }

            ],
            options: [{
                    name: localeMessages['events.type.CONCERT'],
                    code: '2'
                },
                {
                    name: localeMessages['events.type.DANCE_AND_BALLET'],
                    code: '3'
                },
                {
                    name: localeMessages['events.type.OPERA'],
                    code: '4'
                },
                {
                    name: localeMessages['events.type.MUSICALS'],
                    code: '5'
                },
                {
                    name: localeMessages['events.type.COMEDY'],
                    code: '6'
                },
                {
                    name: localeMessages['events.type.DRAMA'],
                    code: '7'
                },
                {
                    name: localeMessages['events.type.ATHLETICS'],
                    code: '8'
                },
                {
                    name: localeMessages['events.type.BASKETBALL'],
                    code: '9'
                },
                {
                    name: localeMessages['events.type.GOLF'],
                    code: '10'
                },
                {
                    name: localeMessages['events.type.SOCCER'],
                    code: '11'
                },
                {
                    name: localeMessages['events.type.EXHIBITIONS'],
                    code: '12'
                },
                {
                    name: localeMessages['events.type.FESTIVALS'],
                    code: '13'
                },
                {
                    name: localeMessages['events.type.VISITOR_ATTRACTIONS'],
                    code: '14'
                },
                {
                    name: localeMessages['events.type.TRANSPORT'],
                    code: '15'
                },
                {
                    name: localeMessages['events.type.MUSEUMS'],
                    code: '16'
                },
                {
                    name: localeMessages['events.type.FAMILY_SHOWS'],
                    code: '17'
                },
                {
                    name: localeMessages['events.type.SHOPPING'],
                    code: '18'
                },
                {
                    name: localeMessages['events.type.DISCOUNTS'],
                    code: '19'
                },
                {
                    name: localeMessages['events.type.EXCURSIONS'],
                    code: '20'
                }
            ]
        }
    },
    methods: {
        changeForm: function(form) {
            var self = this;
            self.activeForm = form;
        },
        searchPlace: function() {
            var self = this;
            if (self.place.url.length == 0) {
                console.error("Place Url is empty");
                return;
            }
            location.href = self.place.url + self.region.queryString;
        },
        searchEvent: function() {
            var self = this;

            var url = self.event.url;

            if (!url) {
                console.log("unchosen url");
                return;
            }


            var queryParam = "";
            if (self.region.queryString) {
                queryParam += self.region.queryString;
            }
            if (self.dateTimePicker) {
               queryParam +=  (queryParam.length > 0) ? "&" : "?";
               queryParam += "b=" + self.dateTimePicker.start;
               if (self.dateTimePicker.end) {
                   queryParam += "&e=" + self.dateTimePicker.end;
               }
            }
            location.href = url;
        }
    },
    computed: {
        now: function() {
            return Date.now()
        },
        language: function() {
            return document.getElementsByTagName("html")[0].getAttribute("data-language");
        },
        id: function() {
            return document.getElementsByTagName("html")[0].getAttribute("data-place-id");
        },
        userId: function() {
            return document.getElementsByTagName("html")[0].getAttribute("data-user-id");
        }

    },
    mounted() {

    }
}
</script>