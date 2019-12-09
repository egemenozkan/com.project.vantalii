<template>
    <div id="searchForm2">
        <ul class="v-form_nav list">
            <li :class="activeForm =='place' ? 'active' : ''" @click="changeForm('place')"><i class="material-icons">near_me</i><button>Places</button></li>
            <li :class="activeForm =='event' ? 'active' : ''" @click="changeForm('event')"><i class="material-icons">local_activity</i><button>Events</button></li>
        </ul>
        <form class="v-form" v-if="activeForm == 'place'">
            <div class="v-form_inner place-search">
                <div class="search">
                    <i class="material-icons">search</i>
                    <autocomplete id="" :owner="place" placeholder="What are you looking for?" autocompleteUrl="/places/autocomplete" />
                </div>
                <div class="region">
                    <i class="material-icons">near_me</i>
                    <autocomplete id="" :owner="region" placeholder="What are you looking for?" autocompleteUrl="/places/autocomplete" />
                </div>
                <div class="v-button">
                    <button class="btn btn-submit">Ara</button>
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
                    <autocomplete :owner="region" placeholder="What are you looking for?" autocompleteUrl="/places/autocomplete" />
                </div>
                <div class="time">
                    <i class="material-icons">access_time</i>
                    <autocomplete :owner="dateTime" placeholder="Tarih, Zaman, Periyot" />
                </div>
                <div class="v-button">
                    <button class="btn btn-submit">Ara</button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
import axiosApi from 'axios';
import autocomplete from '../components/VueAutocomplete.vue';
export default {
    name: 'searchForm',
    components: {
        'autocomplete': autocomplete
    },
    data() {
        return {
           activeForm : 'place',
           place: {},
           region: {},
           event: {},
           dateTime: {}
        }
    },
    methods: {
        changeForm: function(form) {
            var self = this;
           self.activeForm = form;
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