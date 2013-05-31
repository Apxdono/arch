package com.kmware.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company extends DBObject {

	private static final long serialVersionUID = -2752211733896027085L;
	private List<City> cities;

	public Company() {
		cities = new ArrayList<City>(0);
	}

	@ManyToMany
	@JoinTable(name = "cities_companies", 
		joinColumns = { 
			@JoinColumn(name = "company_id", referencedColumnName = "id") 	
		}, 
		inverseJoinColumns = { 
			@JoinColumn(name = "city_id", referencedColumnName = "id") 
		}
	)
	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

}
