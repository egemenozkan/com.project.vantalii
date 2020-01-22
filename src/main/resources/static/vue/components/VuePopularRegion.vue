<template>
    <div v-closable="{
                        exclude: ['populars'],
                        handler: 'onClose'
                      }">
        <input type="text" name="" :value="owner.value" @click="showPopulars" />
        <div :class="'i-populars ' + (populars.visible ? 'show' : '')" ref="populars">
            <div class="popular-cities">
                <span :class="(selections.districts.length == 0 && selections.regions.length == 0) ? 'selected' : ''" @click="selectCity">{{ populars.cities[0].name }}</span>
            </div>
            <div class="popular-districts">
                <h5>İlçeler</h5>
                <span v-for="(district, index) in populars.districts" :class="selections.districts.indexOf(district.id) != -1  || selections.districts.length == 0 ? 'selected' : ''" @click="selectDistrict(district.id)">{{ district.name }}</span><i>...</i>
            </div>
            <div class="popular-regions" v-if="populars.regions.length > 0">
                <h5>Bölgeler</h5>
                <span v-for="(region, index) in populars.regions" :class="selections.regions.indexOf(region.id) != -1 ? 'selected' : ''" @click="selectRegion(region.id)">{{ region.name }}</span>
            </div>
        </div>
    </div>
</template>

<script>
import axiosApi from 'axios';

export default {
    name: 'popularRegion',
    props: {
        owner: Object,
        placeholder: String,
        properties: {
            type: Object,
            default: function() {
                return { suggestions: true, populars: true, debug: false }
            }
        }
    },
    data() {
        return {
            selections: {
                cities: [1],
                districts: [],
                regions: [],
            },
            populars: {
                visible: false,
                cities: [{ id: 1, name: "Tüm Antalya" }],
                districts: [],
                regions: []
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

            self.owner.value = "";
            self.populars.visible = true;
        },
        selectCity() {
            var self = this;
            self.selections.districts = [];
            self.selections.regions = [];
            self.populars.regions = [];
            queryStringGenerator(self);


        },
        selectDistrict(id) {
            var self = this;
            self.selections.cities = [];
            var index = self.selections.districts.indexOf(id);
            if (index != -1) {
                self.$delete(self.selections.districts, index)
            } else {
                self.selections.districts = [];
                getRegionsByDistrict(self, id);
                self.populars.districts.find(element => element.id == id);
                self.owner.value = self.populars.districts.find(element => element.id == id).name;
                self.selections.districts.push(id);
            }
            queryStringGenerator(self);


        },
        selectRegion(id) {
            var self = this;
            var index = self.selections.regions.indexOf(id);
            if (index != -1) {
                self.$delete(self.selections.regions, index)
            } else {
                self.selections.regions.push(id);
            }
            queryStringGenerator(self);


        },
        onClose() {
            var self = this;
            self.populars.visible = false;
            if (self.selections.districts.length == 0 && self.selections.regions.length == 0) {
                self.owner.value = self.populars.cities[0].name;
            } else if (self.selections.regions.length > 0) {
                self.owner.value = self.populars.regions.filter(v => self.selections.regions.indexOf(v.id) != -1).map(v => v.name).join(", ");
            } else if (self.selections.districts.length > 0) {
                self.owner.value = self.populars.districts.filter(v => self.selections.districts.indexOf(v.id) != -1).map(v => v.name).join(", ");
            }

        }

    },
    mounted() {
        const self = this;

        self.owner.value = "Tüm Antalya";

        var getDistrictsUrl = "/gis/cities/1/districts?order=true"
        axiosApi.get(getDistrictsUrl).then(function(response) {
            if (response && response.data) {
                self.populars.districts = response.data;
            }
        }).catch(function(error) { // handle error 
            console.log(error);
        }).finally(function() {});


    }
};

function getRegionsByDistrict(self, districtId) {
    var getRegionsUrl = "/gis/districts/" + districtId + "/regions?order=true"
    axiosApi.get(getRegionsUrl).then(function(response) {
        if (response && response.data) {
            self.populars.regions = response.data;
            self.selections.regions = [];
            for (var i = 0; i < self.populars.regions.length; i++) {
                self.selections.regions.push(self.populars.regions[i].id);
            }
        }
    }).catch(function(error) { // handle error 
        console.log(error);
    }).finally(function() {});
}

function queryStringGenerator(self) {
    self.owner.queryString = "";
    if (self.selections.cities.length > 0) {
        self.selections.cities.forEach(function(element, index, array) {
            self.owner.queryString += index == 0 ? '?' : '&'
            self.owner.queryString += "city=";
            self.owner.queryString += element;
        });
    } else if (self.selections.regions.length == self.populars.regions.length) {
        self.selections.districts.forEach(function(element, index, array) {
            self.owner.queryString += index == 0 ? '?' : '&'
            self.owner.queryString += "district=";
            self.owner.queryString += element;
        });
    } else {
        self.selections.regions.forEach(function(element, index, array) {
            self.owner.queryString += index == 0 ? '?' : '&'
            self.owner.queryString += "region=";
            self.owner.queryString += element;
        });
    }
}
</script>

<style>
.i-populars .popular-cities,
.i-populars .popular-districts,
.i-populars .popular-regions {
    padding: 5px;
}

.i-populars .popular-cities span,
.i-populars .popular-districts span,
.i-populars .popular-regions span {
    display: inline-block;
    padding: 3px 5px;
    border: 1px solid #d6d6d6;
    border-radius: 6px;
    margin: 3px;
    font-size: 0.75rem;
    font-weight: bold;
}

.i-populars .popular-cities span.selected,
.i-populars .popular-districts span.selected,
.i-populars .popular-regions span.selected {
    background-color: #FE8A4F;
    color: #FFF;
    border: none;
}

.i-populars .popular-cities h5,
.i-populars .popular-districts h5,
.i-populars .popular-regions h5 {
    font-size: 0.8rem;
    border-bottom: 1px solid #d6d6d6;
    margin: 0;
    padding: 3px;
}
</style>