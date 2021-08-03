package com.vantalii.web.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.api.data.enums.PeriodType;
import com.project.api.data.model.DailyWorkingHours;
import com.project.api.data.model.event.TimeTable;
import com.vantalii.web.service.ITimeService;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TimeService extends BaseApiService implements ITimeService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${security.api.auth-server-url}")
	private String authServerUrl;

	private static final String CACHE_KEY = "TIME_SERVICE";

	final Logger logger = LoggerFactory.getLogger(TimeService.class);

	@Override
	public List<DailyWorkingHours> getDailyWorkingHours(long id) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/places/" + id + "/time-table");

		List<TimeTable> timetables = getList(endpoint.toString(), new ParameterizedTypeReference<List<TimeTable>>() {
		});

		if (CollectionUtils.isEmpty(timetables)) {
			return Collections.emptyList();
		}

		List<DailyWorkingHours> dailyWorkingHours = new ArrayList<>();
		if (timetables.size() == 1 && timetables.get(0).getStartDate() != null
				&& timetables.get(0).getEndDate() != null) {

			long days = java.time.temporal.ChronoUnit.DAYS.between(timetables.get(0).getStartDate(),
					timetables.get(0).getEndDate());
			if (days >= 7 && timetables.get(0).getPeriodType() == PeriodType.ALL) {
				for (int day = 1; day <= 7; day++) {
					dailyWorkingHours.add(new DailyWorkingHours(day, timetables.get(0).getStartTime(),
							timetables.get(0).getEndTime(), (LocalDate.now().getDayOfWeek().getValue() == day), false));

				}
			}

		}
		return dailyWorkingHours;
	}

}
