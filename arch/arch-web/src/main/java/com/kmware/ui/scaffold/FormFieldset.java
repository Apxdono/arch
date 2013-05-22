package com.kmware.ui.scaffold;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kmware.ui.annotations.UIFieldset;

public class FormFieldset implements Serializable {
	private static final long serialVersionUID = -145124544924397790L;
	
	private String id;
	private String name;
	private List<FormField> fields;
	
	public FormFieldset() {
		id = UIFieldset.COMMON_ID;
		name = UIFieldset.COMMON_LABEL;
		fields = new ArrayList<FormField>(0);
	}
	
	public String resourceBundle;
	
	public String getResourceBundle() {
		return resourceBundle;
	}
	
	public void setResourceBundle(String resourceBundle) {
		this.resourceBundle = resourceBundle;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FormField> getFields() {
		return fields;
	}
	public void setFields(List<FormField> fields) {
		this.fields = fields;
	}
	
	
	
	
	
}
