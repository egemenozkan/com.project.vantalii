<template>
    <div id="searchForm">
        <div class="container">
            <div class="row">
                <div class="col-lg-4">
                    <div class="i-input">
                        <label>Bölge</label>
                        <autocomplete :owner="location" placeholder="İlçe, bölge, semt" autocompleteUrl="/places/autocomplete" />
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="i-input">
                        <label>Etkinlik, Mekan</label>
                        <autocomplete :owner="searchTerm" placeholder="Etkinlik, mekan" autocompleteUrl="/events/autocomplete" />
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="i-input">
                        <label>Tarih, Zaman</label>
                    <autocomplete :owner="dateTime" placeholder="Tarih, Zaman, Periyot" />
                    </div>
                </div>
                <div class="col-lg-12">
                </div>
            </div>
        </div>
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
           location: {},
           searchTerm: {},
           dateTime: {}
        }
    },
    methods: {
        addComment: function() {
            var self = this;
            var newComment = {
                id: 0,
                title: self.comment.title,
                message: self.comment.message,
                user: {
                    id: self.userId
                },
                language: self.language

            };
            axiosApi.post('/places/' + this.id + '/comments', newComment)
                .then(function(response) {
                    if (response.data.success) {
                        self.comments = response.data.comments;
                    }
                })
                .catch(function(error) {
                    console.log(error);
                })
                .then(function() {
                    // always executed
                });
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
        //   var self = this;
        //   if  (self.userId > 0) {
        //     self.online = true;
        //   }
        //   console.log(self.userId + "----userId-----");
        //   axiosApi.get('/places/comments', {
        //       params: {
        //        id: self.id
        //       }
        //     })
        //     .then(function (response) {
        //       if (response.data.success) {
        //           self.comments = response.data.comments;
        //           console.log(self.comments);
        //       }
        //     })
        //     .catch(function (error) {
        //       console.log(error);
        //     })
        //     .then(function () {
        //       // always executed
        //     });  
    }
}
</script>