package com.kmware.web.bbean.request;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import javax.inject.Named;

import org.openfaces.component.table.CSVTableDataFormatter;
import org.openfaces.component.table.DataScope;
import org.openfaces.component.table.DataTable;
import org.openfaces.component.table.TableDataFormatter;

import com.kmware.datatable.CustomXLSTableDataFormatter;
import com.kmware.util.FacesUtils;

@RequestScoped
@Named
public class TableExporter {
	
	private String tableId = "table";
	private String type = "csv";
	private boolean all = false;
	
	@Inject
	Navigation navigation;
	
	public String getTableId() {
		return tableId;
	}
	
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isAll() {
		return all;
	}
	
	public void setAll(boolean all) {
		this.all = all;
	}
	
	
	public void exportDataListener(ActionEvent event){
		this.exportData();
	}
	
	public String exportData(){
		DataTable table = (DataTable) FacesUtils.findComponent(tableId);
		if(table == null) return null;
		TableDataFormatter formatter = null;
		DataScope ds = isAll()?DataScope.FILTERED_ROWS:DataScope.DISPLAYED_ROWS;;
		if(type.equals("csv")){
			formatter = new CSVTableDataFormatter();
		}
		if(type.equals("xls")){
			formatter = new CustomXLSTableDataFormatter();
		}
		table.export(ds, formatter,"report"+"."+type);
		return null;
	}
	
	protected boolean isAjaxRequest(){
		return FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
	}
}
