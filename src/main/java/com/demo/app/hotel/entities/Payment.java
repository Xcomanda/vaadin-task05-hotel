package com.demo.app.hotel.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Payment implements Serializable {

	@Column(name = "PAYMENT")
	private Integer paymentValue;

	public Payment(Payment value) {
		super();
		paymentValue = value.getPayment();
	}

	public Payment() {
		super();
	}

	public Integer getPayment() {
		return paymentValue;
	}

	public void setPayment(Integer payment) {
		this.paymentValue = payment;
	}
}
