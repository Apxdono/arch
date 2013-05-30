package com.kmware.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="countries")
public class Country extends DBObject {
	private static final long serialVersionUID = -8023483403978743105L;
	
	private String flag;
	private List<City> cities;
	
	public Country() {
		this.flag="";
	}
	
	@Column(name="flag")
	public String getFlag() {
		return flag;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="country")
	public List<City> getCities() {
		return cities;
	}
	

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}
