<#ftl encoding="utf-8">
<#import "*/imports/spring.ftl" as spring/>
<#import "*/imports/formatter.ftl" as formatter/>
<#import "*/imports/utils.ftl" as utils/>

<!-- Page Properties -->
<#assign title ="" />
<#assign description>
    <@spring.message "description.places.mainType.ALL"/>
</#assign>
<#assign category = "places">
<#assign styles = []>
<#assign javascripts = ["searchVue"]>
<#assign bundle = "places_index">

<@layout.extends name="layouts/places/indexPlaces.ftl">
    <@layout.put block="header">
    </@layout.put>
    <@layout.put block="contents">
    Deneme
        <div id="tourSearchFormApp"></div>
    </@layout.put>
    <@layout.put block="footer">
     <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.19.0/axios.min.js"></script>	
 <script>
	var categoryList = [
		{ url: "/tur/tip/kultur-turlari-1", label: "Kültür Turları" },
		{ url: "/tur/tip//yurt-disi-turlari-2", label: "Yurt Dışı Turlar" },
		{ url: "/tur/tip/gemi-turlari-3", label: "Gemi Turları" },
		{ url: "/tur/tip/hac-umre-turlari-5", label: "Hac Umre Turları" },
		{ url: "/tur/tip/inanc-turlari-6", label: "İnanç Turları" }
	]

</script>
<style>

.i-autocomplete input{
    font-size: 16px;
    min-height: 60px;
}

.i-autocomplete input:focus {
	box-shadow: none;
	border-color: #e6e6e6;

}
.autocomplete-result {
	    border-radius: 0 0 3px 3px;
    box-shadow: none;
    border: 1px solid #e0e0e0;
    border-top: none;
    box-sizing: content-box;
    margin-top: -2px;
}

ul.autocomplete-result {
	padding-inline-start: 5px;	
	background: #fff;
	margin-top: 0px;
}
ul.autocomplete-result li {
    font-size: 12px;
    color: #000;
    cursor: pointer;
    transition: 0.2s;
    padding: 4px 0px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    line-height: 30px;
    text-align: left;
    border-bottom: 1px solid #e6e6e6;
}

</style>
<script type="text/x-template" id="template-tourSearchForm">
		<div class="row mx-3">

			<div class="col-lg-7 col-md-12 col-12 search-col">
				<label>
					<i class="bg-hotel-placeholder"></i><fmt:message key="form.search.tour.input.tour.label" />
				</label>
    			<autocomplete :owner="tour" slass="form-control" v-closable="{ exclude: ['list'], handler: 'onClose'}" 
					placeholder="Placeholder" ref="autocomplete"></autocomplete>
			</div>
			<div class="col-lg-3 col-md-12 col-12 search-col">
				<label>
					<i class="bg-hotel-placeholder"></i><fmt:message key="form.search.tour.input.period.label" />
				</label>
				<span class="font-size-14 form-control" v-text="period.value" @click="periodTable('click')" v-on:blur="periodTable('blur')">></span>
				<!--  style="width: calc(100% - 10px); background: #FFF; height: 200px; position:absolute;" -->
				<div id="panelRangeDate" v-if="period.show">
					<div>
					</div>
					<periods :owner="period" />
					<div>
					</div>
				</div>
				
				<span class="input-error hotel-text d-none">* Lütfen otel, şehir, bölge seçiniz.</span>
			</div>
			<div class="col-lg-2 col-3">
				<button class="btn btn-primary btn-block btn-search-tour" type="button" @click="searchTour">ARA</button>
			</div>
		</div>	
</script>

<script type="text/x-template" id="periods-template">
	<div>
		<div class="months">
		<h6 class="font-weight-bold">Aylar</h6>
			<span v-for="(month, index) of months" class="rangeDate" :data-selected="month.checked ? 1 : 0" @click="selectMonth(index)">{{ month.label }}</span>
		</div>
		<div class="tags">
		<h6 class="font-weight-bold pt-4">Özel Günler</h6>
			<span v-for="(tag, index) of tags" class="rangeDate" :data-selected="tag.checked ? 1 : 0" @click="selectTag(index)">{{ tag.name }}</span>
		</div>
	</div>
</script>
<script type="text/x-template" id="autocomplete-template">
	<div class="i-autocomplete">
			<input type="text" :class="slass" v-model="owner.value"
				@input="update($event.target.value)"
				@keydown.down = 'down'
          		@keydown.up = 'up'
				@click = 'click'  
		  		 />
      <div v-if="suggestions.visible">
      	<ul class="autocomplete-result"  ref="list">
        	<li v-for="(item,index) in items" :class="{'active': isActive(index)}" @click="selectSuggestion(index)" v-html="item.label"></li>
        </ul>
      </div>
	  <div v-if="populars.visible">
		<ul class="autocomplete-result" ref="list">
			<template v-for="popular in populars.list">
				<li @click="selectPopular(popular.url,popular.label)">{{ popular.label }}</li>
			</template>
		</ul>
	  </div>
	</div>
</script>
  <#--     <#include '*/common/seoPlaces.ftl'>   -->
    </@layout.put>
</@layout.extends>
