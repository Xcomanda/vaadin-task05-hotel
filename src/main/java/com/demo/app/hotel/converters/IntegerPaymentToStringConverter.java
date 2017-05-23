package com.demo.app.hotel.converters;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

@SuppressWarnings("serial")
public class IntegerPaymentToStringConverter implements Converter<String, Integer> {

	@Override
	public Result<Integer> convertToModel(String value, ValueContext context) {
		if (value == null || value.length() == 0) {
			Result.ok(null);
		}
		try {
			return Result.ok(Integer.valueOf(value));
		} catch (NumberFormatException e) {
			return Result.ok(null);
		}
	}

	@Override
	public String convertToPresentation(Integer value, ValueContext context) {
		if (value == null) {
			return "";
		}
		return String.valueOf(value);
	}

}
