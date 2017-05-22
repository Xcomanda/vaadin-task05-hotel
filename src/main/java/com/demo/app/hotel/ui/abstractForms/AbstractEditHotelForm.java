package com.demo.app.hotel.ui.abstractForms;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.validator.routines.UrlValidator;

import com.demo.app.hotel.converters.DateToWorkedDaysConverter;
import com.demo.app.hotel.converters.IntegerRatingToStringConverter;
import com.demo.app.hotel.entities.Hotel;
import com.demo.app.hotel.entities.HotelCategory;
import com.demo.app.hotel.services.CategoryService;
import com.demo.app.hotel.services.HotelService;
import com.demo.app.hotel.ui.HotelForm;
import com.demo.app.hotel.ui.PaymentField;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public abstract class AbstractEditHotelForm extends abstractMultiFunctionalForm {

	protected TextField name;
	protected TextField address;
	protected TextField rating;
	protected DateField operatesFrom;
	protected NativeSelect<HotelCategory> hotelCategory;
	protected TextField url;
	protected TextArea description;
	protected Button save;
	protected Button cancel;
	protected Binder<Hotel> binder;
	protected HotelService hotelService;
	protected PaymentField paymentField;

	protected HotelForm hotelForm;

	protected HorizontalLayout buttons;

	public AbstractEditHotelForm(HotelForm hotelForm) {
		this.hotelForm = hotelForm;
		initComponents();
		initLayouts();
		initComponentsProperties();
		setComponentsToLayouts();
		bindFields();
		addListeners();
	}

	@Override
	protected void bindFields() {
		binder.forField(name).asRequired("Name is required").bind(Hotel::getName, Hotel::setName);
		binder.forField(address).asRequired("Address is required").bind(Hotel::getAddress, Hotel::setAddress);
		binder.forField(rating).withConverter(new IntegerRatingToStringConverter())
				.withValidator(rating -> rating < 6 && rating > 0,
						"Rating is required and should be a number from 1 to 5")
				.bind(Hotel::getRating, Hotel::setRating);
		binder.forField(operatesFrom).withConverter(new DateToWorkedDaysConverter())
				.withValidator(date -> Duration
						.between(LocalDate.now().atTime(0, 0), operatesFrom.getValue().atTime(0, 0)).toDays() <= 0,
						"Cannot set future date")
				.bind(Hotel::getOperatesDays, Hotel::setOperatesDays);
		binder.forField(hotelCategory).withValidator(category -> category != null, "Select category")
				.bind(Hotel::getCategory, Hotel::setCategory);
		binder.forField(url).asRequired("URL is required")
				.withValidator(url -> new UrlValidator().isValid(url), "URL is invalid. Try to write entire URL")
				.bind(Hotel::getUrl, Hotel::setUrl);
		binder.forField(description).bind(Hotel::getDescription, Hotel::setDescription);
		binder.forField(paymentField).withValidator(payment -> {
			return payment.getPayment() == null || payment.getPayment() >= 0 && payment.getPayment() <= 100;
		}, "Select either Cash on arrival or Credit card and input number from 0 to 100").bind(Hotel::getPayment,
				Hotel::setPayment);
	}

	@Override
	protected void initComponents() {
		name = new TextField("Name");
		address = new TextField("Address");
		rating = new TextField("Rating");
		operatesFrom = new DateField("Operates from");
		hotelCategory = new NativeSelect<>("Category");
		url = new TextField("URL");
		description = new TextArea("Description");
		save = new Button("Save");
		cancel = new Button("Cancel");
		binder = new Binder<>(Hotel.class);
		hotelService = HotelService.getInstance();
		paymentField = new PaymentField("Payment");

		List<HotelCategory> categories = CategoryService.getInstance().findAll();
		hotelCategory.setItemCaptionGenerator(category -> category.getName());
		hotelCategory.setItems(categories);
	}

	@Override
	protected void initComponentsProperties() {
		name.setWidth("600px");
		address.setWidth("600px");
		rating.setWidth("50px");
		operatesFrom.setWidth("200px");
		hotelCategory.setWidth("200px");
		url.setWidth("600px");
		description.setWidth("600px");
		description.setHeight("70px");

		setSizeUndefined();
		name.setDescription("Name of hotel");
		address.setDescription("Adress of hotel");
		rating.setDescription("Rating of hotel");
		operatesFrom.setDescription("Foundation date of hotel");
		hotelCategory.setDescription("Category of hotel");
		url.setDescription("Website address");
		description.setDescription("Description of hotel");
		save.setDescription("Save changes");
		cancel.setDescription("Cancel");

		name.setRequiredIndicatorVisible(true);
		address.setRequiredIndicatorVisible(true);
		rating.setRequiredIndicatorVisible(true);
		operatesFrom.setRequiredIndicatorVisible(true);
		operatesFrom.setRangeEnd(LocalDate.now());
		operatesFrom.setTextFieldEnabled(false);
		hotelCategory.setRequiredIndicatorVisible(true);
		url.setRequiredIndicatorVisible(true);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		cancel.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
	}

	@Override
	protected void initLayouts() {
		buttons = new HorizontalLayout();
	}

	@Override
	protected void setComponentsToLayouts() {
		buttons.addComponents(save, cancel);
		addComponents(name, address, rating, operatesFrom, hotelCategory, url, description, paymentField, buttons);
	}

	@Override
	protected void addListeners() {
		save.addClickListener(e -> save());
		cancel.addClickListener(e -> hotelForm.closePopup());
	}

	protected abstract void save();

}
