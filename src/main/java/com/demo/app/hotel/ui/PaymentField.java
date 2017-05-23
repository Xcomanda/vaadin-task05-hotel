package com.demo.app.hotel.ui;

import com.demo.app.hotel.converters.IntegerPaymentToStringConverter;
import com.demo.app.hotel.entities.Payment;
import com.vaadin.data.Binder;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class PaymentField extends CustomField<Payment> {

	private RadioButtonGroup<String> radioBtn;
	private TextField valueTxt;
	private Label label;
	private Payment value;
	private Binder<Payment> binder;
	private String caption;
	private Integer oldValue;
	private Integer newValue;
	private boolean noteProcessed = true;

	public PaymentField(String caption) {
		super();
		this.caption = caption;
		getContent();
	}

	@Override
	public Payment getValue() {
		binder.validate();
		if (radioBtn.getSelectedItem().get() == "Cash") {
			Payment bufPayment = new Payment();
			bufPayment.setPaymentValue(null);
			return bufPayment;
		}
		if (binder.isValid()) {
			return value;
		}
		Payment bufPayment = new Payment();
		bufPayment.setPaymentValue(-1);
		return bufPayment;
	}

	@Override
	protected Component initContent() {
		VerticalLayout vertL = new VerticalLayout();
		vertL.setMargin(false);
		super.setCaption(caption);
		radioBtn = new RadioButtonGroup<>();
		radioBtn.setItems("Credit Card", "Cash");
		radioBtn.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		radioBtn.setSelectedItem("Cash");
		radioBtn.addSelectionListener(e -> {
			if (radioBtn.getSelectedItem().get() == "Cash") {
				valueTxt.setVisible(false);
				label.setVisible(true);
				Payment bufPayment = new Payment();
				bufPayment.setNoting(true);

				try {
					oldValue = Integer.valueOf(valueTxt.getValue());
					if (oldValue < 0)
						oldValue = -1;
				} catch (NumberFormatException ex) {
					oldValue = -1;
				}

				newValue = null;

				noteProcessed = false;
				setValue(bufPayment);
			}
			if (radioBtn.getSelectedItem().get() == "Credit Card") {
				valueTxt.setVisible(true);
				label.setVisible(false);
				Payment bufPayment = new Payment();
				bufPayment.setNoting(true);

				oldValue = null;

				try {
					newValue = Integer.valueOf(valueTxt.getValue());
					if (newValue < 0)
						newValue = -1;
				} catch (NumberFormatException ex) {
					newValue = -1;
				}

				noteProcessed = false;
				setValue(bufPayment);
			}
		});
		valueTxt = new TextField("Value");
		valueTxt.addValueChangeListener(e -> {
			oldValue = newValue;
			try {
				newValue = Integer.valueOf(valueTxt.getValue());
				if (newValue < 0)
					newValue = -1;
			} catch (NumberFormatException ex) {
				newValue = -1;
			}
			Payment bufPayment = new Payment();
			bufPayment.setNoting(true);
			noteProcessed = false;
			setValue(bufPayment);
		});
		binder = new Binder<>(Payment.class);
		binder.forField(valueTxt).withConverter(new IntegerPaymentToStringConverter()).withValidator(payment -> {
			return radioBtn.getSelectedItem().get() == "Cash" || payment >= 0 && payment <= 100;
		}, "Input percentage from 1 to 100").bind(Payment::getPaymentValue, Payment::setPaymentValue);

		label = new Label("Payment will be made directly in the hotel");
		vertL.addComponents(radioBtn, valueTxt, label);

		addValueChangeListener(e -> {
			if (!noteProcessed) {
				Notification.show("Payment changed", generateNoteMessage(), Notification.Type.TRAY_NOTIFICATION);
				noteProcessed = true;
			}
		});

		return vertL;
	}

	@Override
	protected void doSetValue(Payment value) {
		if (value.isNoting()) {
			// noting calling
		} else {
			this.value = new Payment(value);
			binder.removeBean();
			binder.setBean(this.value);
			if (this.value.getPaymentValue() == null) {
				radioBtn.setSelectedItem("Cash");
				valueTxt.setVisible(false);
				label.setVisible(true);
			} else {
				radioBtn.setSelectedItem("Credit Card");
				valueTxt.setVisible(true);
				label.setVisible(false);
			}
		}
	}

	private String generateNoteMessage() {
		String str1, str2;
		if (oldValue == null) {
			str1 = "Cash";
		} else if (oldValue == -1) {
			str1 = "Invalid value";
		} else {
			str1 = oldValue.toString();
		}
		if (newValue == null) {
			str2 = "Cash";
		} else if (newValue == -1) {
			str2 = "Invalid value";
		} else {
			str2 = newValue.toString();
		}
		return "from " + str1 + " to " + str2;
	}

}
