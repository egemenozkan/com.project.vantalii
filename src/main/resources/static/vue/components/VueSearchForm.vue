<template>
    <div id="searchForm2">
        <ul class="v-form_nav list">
            <li :class="activeForm =='place' ? 'active' : ''" @click="changeForm('place')"><i
                    class="material-icons">near_me</i><button>Places</button></li>
            <li :class="activeForm =='event' ? 'active' : ''" @click="changeForm('event')"><i
                    class="material-icons">local_activity</i><button>Events</button></li>
        </ul>
        <form class="v-form" v-if="activeForm == 'place'">
            <div class="v-form_inner place-search">
                <div class="search">
                    <i class="material-icons">search</i>
                    <autocomplete id="" :owner="place" placeholder="Ne arıyorsunuz"
                        :popularsdata='[{id: 0, label:"Alışveriş", name:"Alışveriş", url:"/places/m/shopping" }]'
                        autocompleteUrl="/places/autocomplete" />
                </div>
                <div class="region">
                    <i class="material-icons">near_me</i>
                    <autocomplete id="" :owner="region" placeholder="What are you looking for?"
                        autocompleteUrl="/places/autocomplete" />
                </div>
                <div class="v-button">
                    <button type="button" class="btn btn-submit" @click="searchPlace">Ara</button>
                </div>
            </div>
        </form>
        <form class="v-form" v-if="activeForm == 'event'">
            <div class="v-form_inner event-search">
                <div class="search">
                    <i class="material-icons">search</i>
                    <autocomplete :owner="event" placeholder="Etkinlik, mekan" autocompleteUrl="/events/autocomplete" />
                </div>
                <div class="region">
                    <i class="material-icons">near_me</i>
                    <autocomplete :owner="region" placeholder="What are you looking for?"
                        autocompleteUrl="/places/autocomplete" />
                </div>
                <div class="time">
                    <i class="material-icons">access_time</i>
                    <VueCtkDateTimePicker 
                        v-model="dateTimePicker"
                        :format="'YYYY-MM-DD'"
                         :custom-shortcuts="customShortcuts"
                        label="Zaman"
                        color="#000000"
                        :range="true" />
                </div>
                <div class="v-button">
                    <button type="button" class="btn btn-submit" @click="searchEvent">Ara</button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
    import axiosApi from 'axios';
    import autocomplete from '../components/VueAutocomplete.vue';
    import VueCtkDateTimePicker from 'vue-ctk-date-time-picker';
    import 'vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css';

    console.log("popopop", popularPlaces);
    export default {
        name: 'searchForm',
        components: {
            'autocomplete': autocomplete,
            VueCtkDateTimePicker,

        },
        data() {
            return {
                activeForm: 'place',
                place: {
                    populars: popularPlaces
                },
                region: {},
                event: {},
                dateTimePicker:"",
                eventDays: [],
                customShortcuts:[
  { key: 'thisWeek', label: 'This week', value: 'isoWeek' },
  { key: 'lastWeek', label: 'Last week', value: '-isoWeek' },
  { key: 'last7Days', label: 'Last 7 days', value: 7 },
  { key: 'last30Days', label: 'Last 30 days', value: 30 },
  { key: 'thisMonth', label: 'This month', value: 'month' },
  { key: 'lastMonth', label: 'Last month', value: '-month' },
  { key: 'thisYear', label: 'This year', value: 'year' },
  { key: 'lastYear', label: 'Last year', value: '-year' }
],
                // customShortcuts: [{
                //     key: 'tomorrow',
                //         label: localeMessages['datepicker.tomorrow'],
                //         value: '+day',
                //         isSelected: false
                //     },
                //     { key: 'thisWeek',
                //         label: localeMessages['datepicker.thisWeek'],
                //         value: 'week',
                //         isSelected: true
                //     },
                //     {key: 'weekend',
                //         label: localeMessages['datepicker.weekend'],
                //         value: 'weekend',
                //         isSelected: false
                //     },
                //     {key: 'nextWeek',
                //         label: localeMessages['datepicker.nextWeek'],
                //         value: '+week',
                //         isSelected: false
                //     },
                //     {key: 'thisMonth',
                //         label: localeMessages['datepicker.thisMonth'],
                //         value: 'month',
                //         isSelected: false
                //     },
                //     {key: 'nextMonth',
                //         label: localeMessages['datepicker.nextMonth'],
                //         value: '+month',
                //         isSelected: false
                //     }
                // ],

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
            changeForm: function (form) {
                var self = this;
                self.activeForm = form;
            },
            searchPlace: function () {
                var self = this;
                location.href = self.place.url;
                console.log(self.place);
            },
            searchEvent: function () {
                var self = this;
            }
        },
        computed: {
            now: function () {
                return Date.now()
            },
            language: function () {
                return document.getElementsByTagName("html")[0].getAttribute("data-language");
            },
            id: function () {
                return document.getElementsByTagName("html")[0].getAttribute("data-place-id");
            },
            userId: function () {
                return document.getElementsByTagName("html")[0].getAttribute("data-user-id");
            }

        },
        mounted() {

        }
    }
</script>