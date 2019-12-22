package com.project.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.api.data.enums.Language;
import com.project.api.data.model.gis.City;
import com.project.api.data.model.gis.District;
import com.project.api.data.model.gis.Region;
import com.project.api.data.model.gis.TransferRegion;
import com.project.api.data.model.gis.enums.CityEnum;
import com.project.api.data.model.gis.enums.CountryEnum;
import com.project.api.data.model.gis.enums.DistrictEnum;
import com.project.web.service.IGisService;

@Service
@SuppressWarnings("unchecked")
public class GisService extends BaseApiService implements IGisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
   
	@Value("${security.api.auth-server-url}")
	private String authServerUrl;
    
	final Logger logger = LoggerFactory.getLogger(GisService.class);
	
	@Override
	public List<District> getDistrictsByCity(CityEnum city, Language language, boolean order) {
		UriComponentsBuilder endpoint = UriComponentsBuilder
				.fromUriString(authServerUrl + "/api/v1/gis/cities/" + city.getId() + "/districts");
		if (language != null) {
			endpoint.queryParam("language", language.getCode());
		}
		if (order) {
			endpoint.queryParam("order", order);
		}
		
		Object cacheValue = redisTemplate.opsForHash().get("GIS", endpoint.toString());

		if (cacheValue != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("::cache getDistrictsByCity");
			}
			return (List<District>) cacheValue;
		} else {
			List<District> districts = getList(endpoint.toUriString(),
					new ParameterizedTypeReference<List<District>>() {
					});
			if (districts != null) {
				redisTemplate.opsForHash().put("GIS", endpoint.toString(), districts);
			}
			return districts;
		}
	}

	@Override
	public List<Region> getRegionsByDistrict(DistrictEnum district, Language language, boolean order) {
		UriComponentsBuilder endpoint = UriComponentsBuilder
				.fromUriString(authServerUrl + "/api/v1/gis/districts/" + district.getId() + "/regions");
		if (language != null) {
			endpoint.queryParam("language", language.getCode());
		}
		if (order) {
			endpoint.queryParam("order", order);
		}
		
		Object cacheValue = redisTemplate.opsForHash().get("GIS", endpoint.toString());

		if (cacheValue != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("::cache getRegionsByDistrict");
			}
			return (List<Region>) cacheValue;
		} else {
			List<Region> regions = getList(endpoint.toUriString(),
					new ParameterizedTypeReference<List<Region>>() {
					});
			if (regions != null) {
				redisTemplate.opsForHash().put("GIS", endpoint.toString(), regions);
			}
			return regions;
		}
	}

	@Override
	public List<City> getCitiesByCountry(CountryEnum country, Language language, boolean order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransferRegion> getTransferRegions() {
		// TODO Auto-generated method stub
		return null;
	}

}
