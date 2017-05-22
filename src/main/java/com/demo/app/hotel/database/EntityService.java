package com.demo.app.hotel.database;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.demo.app.hotel.services.CategoryService;
import com.demo.app.hotel.services.HotelService;

public class EntityService implements ApplicationContextAware {

	private static ApplicationContext appContext;

	@Override
	@Autowired
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {

	}

	public static HotelService getHotelService() {
		return appContext.getBean("hotelService", HotelService.class);
	}

	public static CategoryService getCategoryService() {
		return appContext.getBean("categoryService", CategoryService.class);
	}

}
