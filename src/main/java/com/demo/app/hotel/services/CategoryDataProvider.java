package com.demo.app.hotel.services;

import java.util.stream.Stream;

import com.demo.app.hotel.database.EntityService;
import com.demo.app.hotel.entities.HotelCategory;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;

@SuppressWarnings("serial")
public class CategoryDataProvider<Filter> extends AbstractBackEndDataProvider<HotelCategory, Filter> {

	private CategoryService categoryService;

	public CategoryDataProvider() {
		CategoryService categoryService = EntityService.getCategoryService();
	}

	@Override
	protected Stream<HotelCategory> fetchFromBackEnd(Query<HotelCategory, Filter> query) {
		return null;
	}

	@Override
	protected int sizeInBackEnd(Query<HotelCategory, Filter> query) {
		return 0;
	}

}
