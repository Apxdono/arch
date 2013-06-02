package com.kmware.web.bbean.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.kmware.model.City;
import com.kmware.model.Country;
import com.kmware.web.bbean.CommonCRUDBean;

@ViewScoped
@ManagedBean(name="cityCRUD")
public class CityCrudBean extends CommonCRUDBean<City> {
	private static final long serialVersionUID = -9022037695214476125L;
	
	private List<Country> countries;
	
	@Override
	protected void initEntity() {
		entity = new City();
	}
	
	public List<Country> getCountries(){
		if(countries == null){
			String query = "SELECT c FROM Country c WHERE c.deleted = false ORDER BY c.displayName ASC";
			countries = dao.getResultList(query, null, 0, 0, Country.class);
			defaultConverter.setEntries(countries);
		}
		return countries;
	}
}
