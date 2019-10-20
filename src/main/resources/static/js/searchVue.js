

function dateToString(d) {
    return ("0" + d.getDate()).slice(-2) + "." + ("0" + (d.getMonth() + 1)).slice(-2) + "." + d.getFullYear()
}

"use strict";

// This variable will hold the reference to
// document's click handler
var handleOutsideClick;
Vue.directive('closable', {
    bind: function bind(el, binding, vnode) {
        // Here's the click/touchstart handler
        // (it is registered below)
        handleOutsideClick = function handleOutsideClick(e) {
            e.stopPropagation(); // Get the handler method name and the exclude array
            // from the object used in v-closable

            var _binding$value = binding.value,
                handler = _binding$value.handler,
                exclude = _binding$value.exclude; // This variable indicates if the clicked element is excluded

            var clickedOnExcludedEl = false;
            exclude.forEach(function (refName) {
                // We only run this code if we haven't detected
                // any excluded element yet
                if (!clickedOnExcludedEl) {
                    // Get the element using the reference name
                    var excludedEl = vnode.context.$refs[refName]; // See if this excluded element
                    // is the same element the user just clicked on
                    console.log(vnode.context.$refs);

                    if (excludedEl) {
                        clickedOnExcludedEl = excludedEl.contains(e.target);
                    }
                }
            }); // We check to see if the clicked element is not
            // the dialog element and not excluded

            if (!el.contains(e.target) && !clickedOnExcludedEl) {
                // If the clicked element is outside the dialog
                // and not the button, then call the outside-click handler
                // from the same component this directive is used in
                vnode.context[handler]();
            }
        }; // Register click/touchstart event listeners on the whole page


        document.addEventListener('click', handleOutsideClick);
        document.addEventListener('touchstart', handleOutsideClick);
    },
    unbind: function unbind() {
        // If the element that has v-closable is removed, then
        // unbind click/touchstart listeners from the whole page
        document.removeEventListener('click', handleOutsideClick);
        document.removeEventListener('touchstart', handleOutsideClick);
    }
});

var componentPeriods = Vue.component('periods', {
    template: '#periods-template',
    props: {
        owner: Object,
        slass: String
    },
    data() {
        return {
            months: [],
            tags: []
        }
    },
    methods: {
        selectMonth: function (index) {
            var self = this;
            self.months[index].checked = !self.months[index].checked;
            var status = self.months[index].checked;
            if (status) {
                var obj = {};
                obj[index] = self.months[index];
                self.owner.months.push(self.months[index]);
            } else {
                for (var i = 0; i < self.owner.months.length; i++) {
                    if (self.owner.months[i].index === index) {
                        self.owner.months.splice(i, 1);
                    }
                }
            }


        },
        selectTag: function (index) {
            var self = this;
            self.tags[index].checked = !self.tags[index].checked;
            var status = self.tags[index].checked;
            if (status) {
                var obj = {};
                obj[index] = self.tags[index];
                self.owner.tags.push(self.tags[index]);
            } else {
                console.log("silme-islevi")
                for (var i = 0; i < self.owner.tags.length; i++) {
                    if (self.owner.tags[i].index === index) {
                        console.log("silmeli", self.owner.tags[i]);
                        self.owner.tags.splice(i, 1);
                    }
                }
            }


        }
    },
    mounted() {
        var self = this;
        var tagsUrl = '/ajax/tour/tags'
        axios.get(tagsUrl)
            .then(function (response) {
                if (response && response.data) {
                    self.tags = response.data;
                }
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
            .finally(function () {
            });
        var today = new Date();
        self.months = getMonths('3-1-2019', '8-1-2020');
        console.log(self.months);


    }
})

var autocompleteComponent = {
    name: 'autocompleteComponent',
    template: '#autocomplete-template',
    props: {
        owner: Object,
        slass: String
    },
    data: function () {
        return {
            suggestions: {
                visible: false
            },
            populars: {
                visible: false,
                list: categoryList
            },
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
        update: function (text) {
            var self = this;
            if (text.length >= 3) {
                self.populars.visible = false;
                self.suggestions.visible = true;
                self.getSuggestions(text);
                console.log(text);
            } else {
                self.suggestions.visible = false;
            }
        },
        up() {
            if (this.current > 0) {
                this.current--
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
        },
        // For highlighting element
        isActive(index) {
            return index === this.current
        },
        selectPopular: function (url, text) {
            var self = this;
            console.log("-->", url, text);
            self.owner.url = url;
            self.owner.value = text;
            self.populars.visible = false;
        },
        selectSuggestion: function (index) {
            var self = this;
            if (self.items[index]) {
                self.owner.value = self.items[index].label.replace('<b>', '').replace('</b>', '');
                self.owner.id = self.items[index].id;
                self.current = index;
                self.owner.url = self.items[index].url;
                self.suggestions.visible = false;
            }
        },
        getSuggestions: function (text) {
            var self = this;
            var regexp = new RegExp('(' + text + ')', 'gi');
            var autocompleteUrl = '/autocomplete?query=' + text;
            axios.get(autocompleteUrl)
                .then(function (response) {
                    if (response && response.data) {
                    	if (response.data.success) {
	                        var suggestions = response.data.items;
	                        self.items = suggestions.filter(item => item.label.toLowerCase().indexOf(text.toLowerCase()) > -1).map(item => {
	                            return {
	                                id: item.id,
	                                label: item.label.replace(regexp, '<b>$1</b>'),
	                                url: item.url
	                            }
	                        });
                    	}
                    }
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
                .finally(function () {
                });

            //'label': item.label.replace(regexp, '<b>$1</b>')) };
            // return items.filter(item => item.label.indexOf(value) > -1);
        },
        onClose() {
            var self = this;
            self.populars.visible = false;
            self.suggestions.visible = false;

        }
    }
};


var tourSearchForm = new Vue({
    template: '#template-tourSearchForm',
    el: '#tourSearchFormApp',
    components: {
        'autocomplete': autocompleteComponent
    },
    data: {
        from: {
            value: '',
            code: '',
            placeholder: ''
        },
        tour: {
            value: '',
            code: '',
            placeholder: '',
            url: ''
        },
        period: {
            value: 'Dönem Seçiniz',
            code: '',
            tags: [],
            months: [],
            placeholder: '',
            show: false
        }

    },
    methods: {
        // tourAutocomplete: function (event) {
        // 	if (event == "click") {
        // 		this.tour.value = "";
        // 		this.tour.autocomplete = true;
        // 	} else if (event == "blur") {
        // 		this.tour.autocomplete = false;
        // 	}
        // },
        periodTable: function (event) {
            if (event == "click") {
                this.period.show = true;
            } else if (event == "blur") {
                this.period.show = false;
            }
        },
        searchTour: function (event) {
            console.log("-->", this.tour.url);

            var self = this;
            var period_url = "";
            period_url += (self.period.months.length > 0 || self.period.tags.length > 0) ? '?' : '';
            for (var m = 0; m < self.period.months.length; m++) {
                period_url += (m > 0) ? '&' : '';
                period_url += 'baslangic-' + (m + 1);
                period_url += '=' + self.period.months[m].first;
                period_url += '&bitis-' + (m + 1);
                period_url += '=' + self.period.months[m].last;
            }
            for (var t = 0; t < self.period.tags.length; t++) {
                period_url += (self.period.months.length > 0 || t > 0) ? '&' : '?';
                period_url += 'tag-' + (t + 1);
            }
            this.tour.url += period_url;
            if (this.tour.url.length > 0) {
                location.href = this.tour.url;
            }
        },
        onClose() {
            var self = this;
            console.log(self);
            tourSearchForm.$refs.autocomplete.onClose();
        }
    }
})
Vue.config.devtools = true;

function getMonths(startDate, endDate) {
    var resultList = [];
    var date = new Date(startDate);
    var endDate = new Date(endDate);
    var monthNameList = ["Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"];
    var index = 0;
    while (date <= endDate) {
        var stringDate = monthNameList[date.getMonth()] + " " + date.getFullYear();

        //get first and last day of month
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);

        resultList.push({
            index: index++,
            str: stringDate,
            first: dateToString(firstDay),
            last: dateToString(lastDay),
            label: stringDate,
            checked: false
        });
        date.setMonth(date.getMonth() + 1);
    }

    return resultList;
};
