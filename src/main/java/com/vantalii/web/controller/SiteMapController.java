package com.vantalii.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.api.data.enums.MainType;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;
import com.project.common.enums.Language;
import com.project.web.model.XmlUrl;
import com.project.web.model.XmlUrlSet;
import com.vantalii.web.service.IPlaceService;

@Controller
public class SiteMapController {

	private static final String URL_RU = "https://www.vantalii.ru/";
	private static final String URL_TR = "https://www.vantalii.com/tr/";
	private static final String URL_EN = "https://www.vantalii.com/en/";

	final Logger logger = LoggerFactory.getLogger(SiteMapController.class);
	
	@Autowired
	private IPlaceService placeService;

	
	
	@GetMapping(value = "/sitemap.xml")
	@ResponseBody
	public XmlUrlSet siteMap(@RequestParam(defaultValue = "ru", required = false) String language) {
		XmlUrlSet xmlUrlSet = new XmlUrlSet();
		create(xmlUrlSet, "sitemap-attractions.xml", language, XmlUrl.Priority.HIGH);
		create(xmlUrlSet, "sitemap-shopping.xml", language, XmlUrl.Priority.HIGH);
		create(xmlUrlSet, "sitemap-entertainment.xml", language, XmlUrl.Priority.HIGH);
		create(xmlUrlSet, "sitemap-food-and-beverage.xml", language, XmlUrl.Priority.HIGH);
		create(xmlUrlSet, "sitemap-health.xml", language, XmlUrl.Priority.HIGH);
		create(xmlUrlSet, "sitemap-lodging.xml", language, XmlUrl.Priority.HIGH);
		create(xmlUrlSet, "sitemap-public-enterprise.xml", language, XmlUrl.Priority.HIGH);
		create(xmlUrlSet, "sitemap-transport.xml", language, XmlUrl.Priority.HIGH);
		return xmlUrlSet;

	}
	
	@GetMapping(value = "/sitemap-attractions.xml")
	@ResponseBody
	public XmlUrlSet siteMapAttractions(@RequestParam(defaultValue = "ru", required = false) String language) {
		return getXmlUrlSet(language, MainType.ATTRACTIONS);
	}
	
	
	@GetMapping(value = "/sitemap-shopping.xml")
	@ResponseBody
	public XmlUrlSet siteMapShopping(@RequestParam(defaultValue = "ru", required = false) String language) {
		return getXmlUrlSet(language, MainType.SHOPPING);
	}
	
	
	@GetMapping(value = "/sitemap-entertainment.xml")
	@ResponseBody
	public XmlUrlSet siteMapEntertainment(@RequestParam(defaultValue = "ru", required = false) String language) {
		return getXmlUrlSet(language, MainType.ENTERTAINMENT);
	}
	
	@GetMapping(value = "/sitemap-food-and-beverage.xml")
	@ResponseBody
	public XmlUrlSet siteMapFoodAndBeverage(@RequestParam(defaultValue = "ru", required = false) String language) {
		return getXmlUrlSet(language, MainType.FOOD_AND_BEVERAGE);
	}
	
	@GetMapping(value = "/sitemap-health.xml")
	@ResponseBody
	public XmlUrlSet siteMapHealth(@RequestParam(defaultValue = "ru", required = false) String language) {
		return getXmlUrlSet(language, MainType.HEALTH);
	}
	
	@GetMapping(value = "/sitemap-lodging.xml")
	@ResponseBody
	public XmlUrlSet sitemapLodging(@RequestParam(defaultValue = "ru", required = false) String language) {
		return getXmlUrlSet(language, MainType.LODGING);
	}
	
	@GetMapping(value = "/sitemap-public-enterprise.xml")
	@ResponseBody
	public XmlUrlSet sitemapPublicEnterprise(@RequestParam(defaultValue = "ru", required = false) String language) {
		return getXmlUrlSet(language, MainType.PUBLIC_ENTERPRISE);
	}
	
	@GetMapping(value = "/sitemap-transport.xml")
	@ResponseBody
	public XmlUrlSet sitemapTransport(@RequestParam(defaultValue = "ru", required = false) String language) {
		return getXmlUrlSet(language, MainType.TRANSPORT);
	}
	

	private XmlUrlSet getXmlUrlSet(String language, MainType mainType) {
		XmlUrlSet xmlUrlSet = new XmlUrlSet();
		PlaceRequest placeRequest = new PlaceRequest();
		placeRequest.setMainType(mainType);
		placeRequest.setHideAddress(true);
		placeRequest.setHideContact(true);
		placeRequest.setHideContent(true);
		placeRequest.setHideImages(true);
		placeRequest.setHideMainImage(true);
		placeRequest.setLanguage(Language.getByCode(language) == Language.NOTSET ? Language.RUSSIAN : Language.getByCode(language));
		List<PlaceLandingPage> placeLandingPages = placeService.getPlaceLandingPages(placeRequest);
		
		if (placeLandingPages != null && !placeLandingPages.isEmpty()) {
			for (PlaceLandingPage placeLandingPage : placeLandingPages) {
				create(xmlUrlSet, placeLandingPage.getSlug(), language, XmlUrl.Priority.MEDIUM);
			}
		}
		return xmlUrlSet;
	}

	private void create(XmlUrlSet xmlUrlSet, String link, String language, XmlUrl.Priority priority) {

		xmlUrlSet.addUrl(new XmlUrl(getSiteUrlByLang(language) + link, priority));
	}

	private String getSiteUrlByLang(String languageCode) {
		if ("en".equalsIgnoreCase(languageCode)) {
			return URL_EN;
		} else if ("tr".equalsIgnoreCase(languageCode)) {
			return URL_TR;
		} else {
			return URL_RU;
		}
	}

}