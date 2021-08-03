package com.vantalii.web.service;

import java.util.List;

import com.project.api.data.model.DailyWorkingHours;

public interface ITimeService {
	/** Place DailyWorkingHours */
	List<DailyWorkingHours> getDailyWorkingHours(long id);
}
