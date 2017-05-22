package com.demo.app.hotel.ui;

import com.demo.app.hotel.entities.Payment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PaymentField extends CustomField<Payment> {

	private RadioButtonGroup<String> radioBtn;
	private TextField valueTxt;
	private Label label;
	private Payment value;

	private String caption;

	public PaymentField(String caption) {
		super();
		this.caption = caption;
		initContent();
	}

	@Override
	public Payment getValue() {
		return value;
	}

	@Override
	protected Component initContent() {
		VerticalLayout vertL = new VerticalLayout();
		super.setCaption(caption);
		radioBtn = new RadioButtonGroup<>();
		radioBtn.setItems("Credit Card", "Cash");
		valueTxt = new TextField("Value");
		valueTxt.addValueChangeListener(e -> {
			try {
				int val = Integer.parseInt(valueTxt.getValue());
				Payment bufPay = new Payment();
				bufPay.setPayment(val);
				setValue(bufPay);
			} catch (Exception ex) {
				Payment bufPay = new Payment();
				bufPay.setPayment(0);
				setValue(bufPay);
			}
		});
		label = new Label("Payment will be made directly in the hotel");
		vertL.addComponents(radioBtn, valueTxt, label);
		radioBtn.addSelectionListener(e -> {
			if (radioBtn.getSelectedItem().get() == "Cash") {
				Payment bufPay = new Payment();
				bufPay.setPayment(null);
				setValue(bufPay);
			}
			updateByClick();
		});

		addValueChangeListener(e -> {
			note();
		});

		return vertL;
	}

	@Override
	protected void doSetValue(Payment value) {
		this.value = new Payment(value);
		updateByValue();
	}

	private void updateByValue() {
		if (getValue() != null) {
			if (value.getPayment() == null) {
				radioBtn.setSelectedItem("Cash");
				valueTxt.setVisible(false);
				label.setVisible(true);
			} else {
				radioBtn.setSelectedItem("Credit Card");
				valueTxt.setValue(value.getPayment() == null ? "" : value.getPayment().toString());
				valueTxt.setVisible(true);
				label.setVisible(false);
			}
		}
	}

	private void updateByClick() {
		if (radioBtn.getSelectedItem().get() == "Cash") {
			valueTxt.setVisible(false);
			label.setVisible(true);
		} else {
			valueTxt.setVisible(true);
			label.setVisible(false);
		}
	}

	private void note() {
		Notification.show("changed");
	}

}
