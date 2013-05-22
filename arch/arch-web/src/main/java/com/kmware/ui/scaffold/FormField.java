package com.kmware.ui.scaffold;

import java.io.Serializable;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

public class FormField implements Serializable {
	private static final long serialVersionUID = -4278025575614877973L;
	private boolean required;
	private ValueExpression el;
	private String label;
	private String expression;
	private int maxLength;
	private boolean isPlaceholder;
	private String fieldName;
	private Class<?> returnType;

	public FormField(String el, String fieldName) {
		maxLength = -1;
		isPlaceholder = false;
		this.fieldName = fieldName;
		this.expression = el;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public ValueExpression getValue() {
		if (el == null) {
			this.el = FacesContext
					.getCurrentInstance()
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(
							FacesContext.getCurrentInstance().getELContext(),
							this.expression, this.returnType);
		}
		return el;
	}

	public void setValue(ValueExpression o) {
		el = o;
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

	public boolean isPlaceholder() {
		return isPlaceholder;
	}

	public void setPlaceholder(boolean isPlaceholder) {
		this.isPlaceholder = isPlaceholder;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
