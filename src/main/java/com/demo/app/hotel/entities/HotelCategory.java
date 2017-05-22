package com.demo.app.hotel.entities;

import java.io.Serializable;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "CATEGORY")
public class HotelCategory implements Serializable, Cloneable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;

	public HotelCategory() {

	}

	public HotelCategory(Integer id) {
		super();
		this.id = id;
	}

	public HotelCategory(String name) {
		super();
		this.name = name;
	}

	public HotelCategory(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HotelCategory))
			return false;
		HotelCategory that = (HotelCategory) obj;
		return id != null ? id.equals(that.id) : that.id == null;
	}

	@Override
	public HotelCategory clone() throws CloneNotSupportedException {
		return (HotelCategory) super.clone();
	}

	@Override
	public String toString() {
		return "HotelCategory [id=" + id + ", name=" + name + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}

}
