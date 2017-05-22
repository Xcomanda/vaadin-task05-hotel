package com.demo.app.hotel.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@Table(name = "HOTEL")
public class Hotel implements Serializable, Cloneable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name = "";

	@Column(name = "ADDRESS")
	private String address = "";

	@Column(name = "RATING")
	private Integer rating;

	@Column(name = "OPERATES_FROM")
	private Long operatesDays = 0L;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "CATEGORY", nullable = true)
	private HotelCategory category;

	@Column(name = "URL")
	private String url;

	@Column(name = "DESCRIPTION", columnDefinition = "text")
	private String description = "";

	@Version
	@Column(name = "OPTLOCK")
	private long version;

	@Embedded
	private Payment payment = new Payment();

	public Hotel() {

	}

	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public String toString() {
		return name + " " + rating + "stars " + address;
	}

	@Override
	public Hotel clone() throws CloneNotSupportedException {
		return (Hotel) super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Long getOperatesDays() {
		return operatesDays;
	}

	public void setOperatesDays(Long operatesDays) {
		this.operatesDays = operatesDays;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HotelCategory getCategory() {
		return category;
	}

	public void setCategory(HotelCategory category) {
		this.category = category;
	}

	public long getVersion() {
		return version;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}