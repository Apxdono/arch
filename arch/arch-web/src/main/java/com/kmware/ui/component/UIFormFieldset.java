package com.kmware.ui.component;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import com.kmware.ui.renderers.FormFieldsetRenderer;
import com.kmware.ui.scaffold.FormFieldset;

@FacesComponent(UIFormFieldset.COMPONENT_TYPE)
public class UIFormFieldset extends UIComponentBase {

	public static final String COMPONENT_FAMILY = "com.kmware";
	public static final String COMPONENT_TYPE = "com.kmware.UIFormFieldset";
	
	 private enum PropertyKeys {
	      	fieldsets;
	 }
	
	
	public UIFormFieldset() {
		super();
	}
	
	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	@Override
	public String getRendererType() {
		return FormFieldsetRenderer.RENDERER_TYPE;
	}
	
	@SuppressWarnings("unchecked")
	public List<FormFieldset> getFieldsets() {
		return (List<FormFieldset>) getStateHelper().eval(PropertyKeys.fieldsets, new ArrayList<FormFieldset>(0));
	}
	
	public void setFieldsets(List<FormFieldset> fieldsets) {
		getStateHelper().put(PropertyKeys.fieldsets, fieldsets);
	}
	
	
	
 
}
