<template>
    <div v-closable="{
        exclude: ['suggestions', 'populars'],
        handler: 'onClose'
      }">
        <input type="text" name="" :placeholder="placeholder" v-model="owner.value" @click="showPopulars" @keyup="getSuggestions($event.target.value)" />
        <div :class="'i-suggestions ' + (suggestions.visible ? 'show' : '')" ref="suggestions">
            <ul>
                <li v-for="(item,index) in suggestions.list" v-html="item.label" @click="selectSuggestion(index)"></li>
            </ul>
        </div>
        <div :class="'i-populars ' + (populars.visible ? 'show' : '')" ref="populars">
            <ul>
                <li v-for="(item, index) in populars.list" :class="index == 0 ? 'active' : ''" @click="selectPopular(index)"> <span v-html="item.label"></span></li>
            </ul>
        </div>
    </div>
</template>

<script>
import axiosApi from 'axios';

export default {
    name: 'autocomplete',
    props: {
        owner: Object,
        slass: String,
        placeholder: String,
        autocompleteUrl: String,
        popularsdata: Array,
        properties: {
            type: Object,
            default: function() {
                return { suggestions: true, populars: true, debug: false }
            }
        }
    },
    data() {
        return {
            suggestions: {
                visible: false,
                list: []
            },
            populars: {
                visible: false,
                list: []
            },
            current: 0
        }

    },
    methods: {
        showPopulars() {
            var self = this;
            if (!self.properties) {
                return;
            }
            self.populars.list = self.owner.populars;
            self.owner.value = "";
            self.populars.visible = true;
        },
        update: function(text) {
            var self = this;
            if (text.length >= 3) {
                self.populars.visible = false;
                self.suggestions.visible = true;
                self.getSuggestions(text);
            } else {
                self.suggestions.visible = false;
            }
        },
        up() {
            if (this.current > 0) {
                this.current--;
                this.owner.value = this.suggestions.list[this.current].label.replace('<b>', '').replace('</b>', '');
            }
        },
        // When down arrow pressed while suggestions are open
        down() {
            if (this.current < this.suggestions.list.length - 1) {
                this.current++;
                this.owner.value = this.suggestions.list[this.current].label.replace('<b>', '').replace('</b>', '');
            }
        },
        blur(target) {
            var self = this;
            self.suggestions.visible = false;
            self.populars.visible = false;
        }, // For highlighting element
        isActive(index) {
            return index === this.current
        },
        selectPopular: function(index) {
            var self = this;
            if (self.populars.list[index]) {
                self.owner.value = self.populars.list[index].name;
                // self.owner.id = self.populars.list[index].id;
                self.current = index;
                self.owner.url = self.populars.list[index].url;
                self.populars.visible = false;
            }
        },
        selectSuggestion: function(index) {
            var self = this;
            if (self.suggestions.list[index]) {
                self.owner.value = self.suggestions.list[index].name;
                self.owner.id = self.suggestions.list[index].id;
                self.current = index;
                self.owner.url = self.suggestions.list[index].url;
                self.suggestions.visible = false;
            }
        },
        getSuggestions: function(text) {
            var self = this;

            // if (self.properties.suggestions && text.length < 3) {
            //     return;
            // }
     if (text.length < 3) {
                return;
            }
            console.log("-->",text);
            self.populars.visible = false;

            var regexp = new RegExp('(' + text + ')', 'gi');
            var autocompleteUrl = self.autocompleteUrl + '?language=' + self.language + '&query=' + text;
            axiosApi.get(autocompleteUrl).then(function(response) {
                if (response && response.data) {
                    if (response.data.success) {
                        var suggestions = response.data.items;
                        self.suggestions.list = suggestions.filter(item => item.label.toLowerCase().indexOf(text
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
            }).catch(function(error) { // handle error 
                console.log(error);
            }).finally(function() {});
        },
        onClose() {
            var self = this;
            self.populars.visible = false;
            self.suggestions.visible = false;
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
        var self = this;
        // self.populars.list = self.owner.populars;
        // console.log(self.owner);
    }
};
</script>

<style>
/* .i-suggestions {
        list-style: none;
        box-shadow: none;
        position: absolute;
        z-index: 2000;
        margin: 5px 0 0 0;
        padding: 0;
        background: #fff;
        width: 100%;
        overflow: auto;
    }

    .i-suggestions li {
        height: 45px;
        line-height: 25px !important;
        padding: 10px;
        display: block;
        border-bottom: solid 1px #ddd;
        overflow: hidden;
        text-decoration: none;
        color: #999;
        cursor: pointer;
        width: 100%;
        -webkit-transition: all 0.25s ease-in-out;
        -moz-transition: all 0.25s ease-in-out;
        -o-transition: all 0.25s ease-in-out;
        -ms-transition: all 0.25s ease-in-out;
    } */

.i-suggestions {
    position: absolute;
    background-color: #fff;
    /* border: 1px solid #aaa; */
    border-radius: 4px;
    box-sizing: border-box;
    display: none;
    position: absolute;
    /* left: -100000px; */
    width: 100%;
    z-index: 1051;
    left: 0;
    border-top: 1px solid rgb(197, 1, 67);
}

@media screen and (min-width: 991px) {
    .i-suggestions {
        width: calc(100% - 20px);
        left: 20px;
    }
}

.i-suggestions.show {
    transition: 1s;
    display: block;
}

.i-suggestions ul {
    padding: 0;
}

.i-suggestions ul li {
    padding: 0;
    list-style: none;
    font-size: 14px;
    font-weight: 400;
    letter-spacing: 0.28px;
    line-height: 23.8px;
    cursor: pointer;
    color: #70778b;
    padding: 8px 15px;
}

.i-suggestions ul li:hover {
    background-color: rgba(197, 1, 67, 0.8) !important;
    color: #FFF;
    -webkit-transition: all 0.25s ease-in-out;
    -moz-transition: all 0.25s ease-in-out;
    -o-transition: all 0.25s ease-in-out;
    -ms-transition: all 0.25s ease-in-out;
}

.i-suggestions ul li span {
    font-weight: bold;
}

.i-populars {
    position: absolute;
    background-color: #fff;
    /* border: 1px solid #aaa; */
    border-radius: 4px;
    box-sizing: border-box;
    display: none;
    position: absolute;
    /* left: -100000px; */
    width: 100%;
    z-index: 1051;
    left: 0;
}

@media screen and (min-width: 991px) {
    .i-populars {
        width: calc(100% - 20px);
        left: 20px;
    }
}

.i-populars.show {
    transition: 1s;
    display: block;
}

.i-populars ul {
    padding: 0;
}

.i-populars ul li {
    padding: 0;
    list-style: none;
    font-size: 14px;
    font-weight: 400;
    letter-spacing: 0.28px;
    line-height: 23.8px;
    cursor: pointer;
    color: #70778b;
    padding: 8px 15px;
}

.i-populars ul li:hover {
    background-color: rgb(197, 1, 67, 0.8) !important;
    color: #FFF;
}

.i-populars ul li span {
    font-weight: bold;
}
</style>