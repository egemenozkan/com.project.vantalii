<template>
    <div>
        <input type="text" name="" :placeholder="placeholder" @input="getSuggestions($event.target.value)" />
        <ul v-if="suggestions.visible" class="i-suggestions">
            <li v-for="(item,index) in items" v-html="item.label"></li>
        </ul>
    </div>
</template>

<script>
import axiosApi from 'axios';
var categoryList = [];
export default {
    name: 'searchForm',
    props: { owner: Object, slass: String, placeholder: String, autocompleteUrl: String },
    data() {
        return {
            suggestions: { visible: false },
            populars: { visible: false, list: categoryList },
            current: 0,
            items: []
        }

    },
    methods: {
        click() {
            this.owner.value = "";
            this.populars.visible = true;
            console.log("clicked");
        },
        update: function(text) {
            var self = this;
            if (text.length >= 3) {
                self.populars.visible = false;
                self.suggestions.visible = true;
                self.getSuggestions(text);
                console.log(text);
            } else { self.suggestions.visible = false; }
        },
        up() {
            if (this.current > 0) {
                this.current--;
                this.owner.value = this.items[this.current].label.replace('<b>', '').replace('</b>', '');
            }
        },
        // When down arrow pressed while suggestions are open
        down() {
            if (this.current < this.items.length - 1) {
                this.current++;
                this.owner.value = this.items[this.current].label.replace('<b>', '').replace('</b>', '');
            }
        },
        blur(target) {
            var self = this;
            console.log(target);
            self.suggestions.visible = false;
            self.populars.visible = false;
        }, // For highlighting element
        isActive(index) { return index === this.current },
        selectPopular: function(url, text) {
            var self = this;
            console.log("-->", url, text);
            self.owner.url = url;
            self.owner.value = text;
            self.populars.visible = false;
        },
        selectSuggestion: function(index) {
            var self = this;
            if (self.items[index]) {
                self.owner.value = self.items[index].label.replace('<b>', '').replace('</b>', '');
                self.owner.id = self.items[index].id;
                self.current = index;
                self.owner.url = self.items[index].url;
                self.suggestions.visible = false;
            }
        },
        getSuggestions: function(text) {
            if (text.length < 3) {
                return;
            }

            var self = this;
            var regexp = new RegExp('(' + text + ')', 'gi');
            var autocompleteUrl = self.autocompleteUrl + '?query=' + text;
            axiosApi.get(autocompleteUrl).then(function(response) {
                if (response && response.data) {
                    if (response.data.success) {
                        var suggestions = response.data.items;
                        self.items = suggestions.filter(item => item.label.toLowerCase().indexOf(text.toLowerCase()) > -1)
                            .map(item => { return { id: item.id, label: item.label.replace(regexp, '<b>$1</b>'), url: item.url } });
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
    }
};
</script>

<style>
.i-suggestions {
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
}
</style>
