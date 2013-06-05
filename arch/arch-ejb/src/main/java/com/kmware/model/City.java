package com.kmware.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cities")
public class City extends DBObject {
	private static final long serialVersionUID = -6329873866699956812L;
	
	private String zipCode;
	private Country country;
	
	public City() {
		zipCode = "";
		country = new Country();
	}
	
	@Column(name="zip_code",length=10)
	public String getZipCode() {
		return zipCode;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="country_id",nullable=false)
	public Country getCountry() {
		return country;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	@Override
	public void loadLazyObjects() {
		super.loadLazyObjects();
		if(country!=null){
			country.getId();
		}
	}
	
}
