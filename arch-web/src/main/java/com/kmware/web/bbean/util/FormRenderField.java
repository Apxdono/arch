package com.kmware.web.bbean.util;

import java.io.Serializable;

public class FormRenderField implements Serializable {
	private static final long serialVersionUID = -339516620985210400L;
	
	private boolean required;
	private Object value;
	private String label;
	private int maxLength;
	
	public FormRenderField() {
		maxLength = -1;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
		
}
