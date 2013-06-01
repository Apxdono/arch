package com.kmware.web.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.kmware.model.DBObject;


public class DefaultConverter implements Converter {

	private List entries;
	
	public List getEntries() {
		return entries;
	}
	
	public void setEntries(List entries) {
		this.entries = entries;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if(entries!=null && entries.size() > 0){
			for (Object entry : entries) {
				if(((DBObject)entry).getId().equals(value)){
					return entry;
				}
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value != null && value instanceof DBObject){
			return ((DBObject)value).getId();
		}
		return null;
	}

}
