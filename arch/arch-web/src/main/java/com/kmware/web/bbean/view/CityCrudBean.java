package com.kmware.web.bbean.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlForm;
import javax.faces.event.AjaxBehaviorEvent;

import org.openfaces.component.table.CSVTableDataFormatter;
import org.openfaces.component.table.DataScope;
import org.openfaces.component.table.DataTable;
import org.openfaces.component.table.TableDataFormatter;
import org.openfaces.util.Faces;

import com.kmware.model.City;
import com.kmware.model.Country;
import com.kmware.web.bbean.CommonCRUDBean;

@ViewScoped
@ManagedBean(name="cityCRUD")
public class CityCrudBean extends CommonCRUDBean<City> {
	private static final long serialVersionUID = -9022037695214476125L;
	
	private List<Country> countries;

	
	public List<Country> getCountries(){
		if(countries == null){
			String query = "SELECT c FROM Country c WHERE c.deleted = false ORDER BY c.displayName ASC";
			countries = dao.getResultList(query, null, 0, 0, Country.class);
			defaultConverter.setEntries(countries);
		}
		return countries;
	}
	
	public void exportToCSV(){
		DataTable table = Faces.component("crudform:table", DataTable.class);
		table.export(DataScope.FILTERED_ROWS, new CSVTableDataFormatter(),"RESULT.csv");
	}
}
