package com.demo.app.hotel.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.demo.app.hotel.entities.Hotel;
import com.demo.app.hotel.ui.abstractForms.AbstractEditHotelForm;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class BulkEditHotelForm extends AbstractEditHotelForm {

	private NativeSelect<AbstractComponent> nativeSelectFields;
	private Set<Hotel> hotels;
	private List<AbstractComponent> fields;
	private AbstractComponent currentComponent;
	private Hotel hotel = new Hotel();

	public BulkEditHotelForm(HotelForm hotelForm) {
		super(hotelForm);
		setWidth("800px");
		nativeSelectFields = new NativeSelect<AbstractComponent>();

		Map<AbstractComponent, String> Mapfields = new HashMap<>();
		Mapfields.put(name, "Name");
		Mapfields.put(address, "Address");
		Mapfields.put(rating, "Rating");
		Mapfields.put(operatesFrom, "Operates From");
		Mapfields.put(hotelCategory, "Category");
		Mapfields.put(url, "Url");
		Mapfields.put(description, "Description");
		Mapfields.put(paymentField, "Payment");
		paymentField.setVisible(false);

		fields = new ArrayList<>();
		fields.add(name);
		fields.add(address);
		fields.add(rating);
		fields.add(operatesFrom);
		fields.add(hotelCategory);
		fields.add(url);
		fields.add(description);
		fields.add(paymentField);

		nativeSelectFields.addSelectionListener(e -> {
			if (currentComponent != null) {
				currentComponent.setVisible(false);
			}
			if (nativeSelectFields.getSelectedItem().isPresent()) {
				currentComponent = nativeSelectFields.getSelectedItem().get();
				currentComponent.setVisible(true);
			} else {
				currentComponent = null;
			}
			selectionCheck();
		});

		nativeSelectFields.setItemCaptionGenerator(field -> Mapfields.get(field));
		nativeSelectFields.setItems(fields);
		nativeSelectFields.setDescription("Selected field");
		VerticalLayout vertLayout = new VerticalLayout();
		List<Component> components = new ArrayList<>();
		forEach(component -> components.add(component));
		removeAllComponents();
		components.forEach(component -> vertLayout.addComponent(component));
		Label label = new Label("Select field to bulk edit. Only selected field will get changes");
		addComponents(label, nativeSelectFields, vertLayout);
		selectionCheck();
	}

	public void setHotel(Set<Hotel> hotels) {
		binder.setBean(hotel);
		this.hotels = hotels;
		currentComponent = null;
		nativeSelectFields.setSelectedItem(null);
		fields.forEach(field -> field.setVisible(false));
	}

	@Override
	protected void save() {
		if (currentComponent != null) {
			binder.validate();
			if (currentComponent.getErrorMessage() == null) {
				changeValues();
				hotels.forEach(hotel -> hotelService.save(hotel));
				hotelForm.closePopup();
				hotelForm.updateList();
			}
		}
	}

	private void changeValues() {
		if (currentComponent == name) {
			hotels.forEach(hotel -> hotel.setName(this.hotel.getName()));
		} else if (currentComponent == address) {
			hotels.forEach(hotel -> hotel.setAddress(this.hotel.getAddress()));
		} else if (currentComponent == rating) {
			hotels.forEach(hotel -> hotel.setRating(this.hotel.getRating()));
		} else if (currentComponent == operatesFrom) {
			hotels.forEach(hotel -> hotel.setOperatesDays(this.hotel.getOperatesDays()));
		} else if (currentComponent == hotelCategory) {
			hotels.forEach(hotel -> hotel.setCategory(this.hotel.getCategory()));
		} else if (currentComponent == url) {
			hotels.forEach(hotel -> hotel.setUrl(this.hotel.getUrl()));
		} else if (currentComponent == description) {
			hotels.forEach(hotel -> hotel.setDescription(this.hotel.getDescription()));
		} else if (currentComponent == paymentField) {
			hotels.forEach(hotel -> hotel.setPayment(paymentField.getValue()));
		}
	}

	private void selectionCheck() {
		save.setEnabled(currentComponent != null);
	}

}
