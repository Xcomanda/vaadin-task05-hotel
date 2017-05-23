package com.demo.app.hotel.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Embeddable
public class Payment implements Serializable {

	@Column(name = "PAYMENT")
	private Integer paymentValue;
	
	@Transient
	private boolean noting = false;

	public Payment(Payment value) {
		super();
		paymentValue = value.getPaymentValue();
	}

	public Payment() {
		super();
	}

	public Integer getPaymentValue() {
		return paymentValue;
	}

	public void setPaymentValue(Integer paymentValue) {
		this.paymentValue = paymentValue;
	}

	public boolean isNoting() {
		return noting;
	}

	public void setNoting(boolean noting) {
		this.noting = noting;
	}
	
}
