package com.kmware.web.bbean.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;

import com.kmware.model.City;
import com.kmware.model.Company;
import com.kmware.model.Country;
import com.kmware.util.FacesUtils;
import com.kmware.web.bbean.CommonCRUDBean;

@ViewScoped
@ManagedBean(name="companyCRUD")
public class CompanyCrudBean extends CommonCRUDBean<Company> {
	private static final long serialVersionUID = -9022037695214476125L;
	
	private List<City> cities;
	private List<String> selected;
	
	
	public List<String> getSelected(){
		if(selected == null){
			selected = new ArrayList<String>(0);
			if(entity.getCities()!=null){
				selected = FacesUtils.pluckIds(entity.getCities());
			}
		}
		
		return selected;
	}
	
	public void setSelected(List<String> selected) {
		this.selected = selected;
		if(selected == null || selected.size() < 1 ) return;
		this.entity.setCities(dao.getFromArray(City.class, this.selected, false));
	}
	
	public SelectItem[] getCities(){
		if(cities == null){
			String query = "SELECT c FROM City c WHERE c.deleted = false ORDER BY c.displayName ASC";
			cities = dao.getResultList(query, null, City.class);
		}
		return FacesUtils.getAsSelectItems(cities);
	}
	
}
