package com.demo.app.hotel.ui;

import com.demo.app.hotel.entities.Hotel;
import com.demo.app.hotel.ui.abstractForms.AbstractEditHotelForm;

@SuppressWarnings("serial")
public class EditHotelForm extends AbstractEditHotelForm {

	private Hotel hotel = new Hotel();

	public EditHotelForm(HotelForm hotelForm) {
		super(hotelForm);
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;

		binder.setBean(hotel);
		if (hotel.getCategory() == null) {
			hotelCategory.setSelectedItem(null);
		}
		name.selectAll();
	}

	@Override
	protected void save() {
		binder.validate();
		if (binder.isValid()) {
			System.out.println(hotel.getPayment().getPayment());
			//hotelService.save(hotel);
			//hotelForm.updateList();
			//hotelForm.closePopup();
		} else {
			binder.validate();
		}
	}
}