<template>
    <div>
        <div class="v-box v-box-list">
            <main class="">
                <div class="v-box_thumbnail">
                    <figure>
                        <img src="https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=370"
                            width="370" />
                    </figure>
                </div>
                <div class="v-box_list_detail">
                    <div class="content">
                        <h3>Mall Of Antalya</h3>
                        <div class="meta">
                            <div class="location">
                                <i class="material-icons">
                                    beenhere
                                </i>
                                <span>Barcelona, ES</span>
                            </div>
                            <div class="number">
                                <i class="material-icons">
                                    phone
                                </i><span>+34 2 2458752</span>
                            </div>
                        </div>
                        <div class="description">
                            The path of the righteous man is beset on all side by the
                            iniquities. Nam in mauris quis libero sod eleifend spectra
                            online.

                        </div>

                    </div>
                    <div class="footer">
                        <div class="category">Mall</div>
                        <div class="status">
                            Open Now
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
</template>

<script>
    import axiosApi from 'axios';

    export default {
        name: 'placeList',
        props: {
            owner: Object,
            slass: String
        },
        data() {
            return {
                places: {
                    visible: true,
                    loading: true,
                    list: []
                },
                current: 0
            }

        },
        methods: {
            getSuggestions: function (text) {
                if (text.length < 3) {
                    return;
                }

                var self = this;
                var regexp = new RegExp('(' + text + ')', 'gi');
                var autocompleteUrl = self.autocompleteUrl + '?query=' + text;
                axiosApi.get(autocompleteUrl).then(function (response) {
                    if (response && response.data) {
                        if (response.data.success) {
                            var suggestions = response.data.items;
                            self.suggestions.list = suggestions.filter(item => item.label.toLowerCase()
                                    .indexOf(text
                                        .toLowerCase()) > -1)
                                .map(item => {
                                    return {
                                        id: item.id,
                                        label: item.label.replace(regexp, '<b>$1</b>'),
                                        name: item.label,
                                        url: item.url
                                    }
                                });
                            self.suggestions.visible = true;

                        }
                    }
                }).catch(function (error) { // handle error 
                    console.log(error);
                }).finally(function () {});
            }

        },
        mounted() {
            var self = this;

        }
    };
</script>

<style>

</style>