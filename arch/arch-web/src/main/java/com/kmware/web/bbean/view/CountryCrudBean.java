package com.kmware.web.bbean.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.kmware.model.Country;
import com.kmware.web.bbean.CommonCRUDBean;

@ViewScoped
@ManagedBean(name="countryCRUD")
public class CountryCrudBean extends CommonCRUDBean<Country> {
	private static final long serialVersionUID = -9022037695214476125L;
	
	@Override
	protected void initEntity() {
		entity = new Country();
	}
}
