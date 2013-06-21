package com.kmware.ui.processors;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.Column;

import org.apache.commons.lang3.text.WordUtils;

import com.kmware.ui.annotations.UIEntity;
import com.kmware.ui.annotations.UIField;
import com.kmware.ui.annotations.UIFieldset;
import com.kmware.ui.scaffold.FormField;
import com.kmware.ui.scaffold.FormFieldset;

public class FormProcessor {

	private String backingBeanName;
	private String rootEntityName;
	private FacesContext context;

	public FormProcessor(String backingBeanName, String rootEntityName) {
		this.backingBeanName = backingBeanName;
		this.rootEntityName = rootEntityName;
	}

	public List<FormFieldset> getFormFieldsets(Object o) {
		List<FormFieldset> result = new ArrayList<FormFieldset>(0);

		if (o != null && o.getClass().isAnnotationPresent(UIEntity.class)) {
			UIEntity annot = o.getClass().getAnnotation(UIEntity.class);
			UIFieldset[] sets = annot.fieldsets();
			result = new ArrayList<FormFieldset>(sets.length);
			Map<String, FormFieldset> bufferSets = new HashMap<String, FormFieldset>(
					sets.length);
			for (UIFieldset fs : sets) {
				FormFieldset ff = new FormFieldset();
				ff.setId(fs.id());
				ff.setName(fs.label());
				ff.setResourceBundle("");//TODO ADD Resource Bundle support
				result.add(fs.position(), ff);
				bufferSets.put(fs.id(), ff);
			}
			
			processVisibleFields(o, bufferSets);

		}
		return result;
	}

	/**
	 * Get methods to process further in renderer
	 * 
	 * @param o
	 *            - object which methods should be processed
	 * @return list of methods if any or empty collection
	 */
	protected List<Method> getMethods(Object o) {
		List<Method> methods = new ArrayList<Method>();
		methods.addAll(Arrays.asList(o.getClass().getDeclaredMethods()));

		Class parent = o.getClass().getSuperclass();
		while (!parent.equals(Object.class)) {
			methods.addAll(Arrays.asList(parent.getDeclaredMethods()));
			parent = parent.getSuperclass();
		}
		return methods;
	}

	protected void processVisibleFields(Object o,Map<String, FormFieldset> bufferSets) {
		List<Method> methods = getMethods(o);

		for (Method method : methods) {
			if (method.getName().startsWith("get")) {
				// We dont need get class for now
				String name = method.getName();
				if (name.equals("getClass")) {
					continue;
				}

				// Chech if we have a ui field
				if (method.isAnnotationPresent(UIField.class)) {
					UIField annot = method.getAnnotation(UIField.class);
					if (Boolean.parseBoolean(annot.showInForm())) {

						String fieldName = WordUtils.uncapitalize(name
								.substring(3));
						String el = "#{" + backingBeanName + "."
								+ rootEntityName + "." + fieldName + "}";

						FormField field = new FormField(el,
								fieldName);
						field.setReturnType(method.getReturnType());
						field.setLabel(new String(annot.label()));
						field.setRequired(Boolean.parseBoolean(annot.required()));
						field.setMaxLength(annot.maxLength());
						field.setPlaceholder(Boolean.parseBoolean(annot.placeholder()));
						bufferSets.get(annot.fieldset()).getFields().add(field);
					}
				}// endif UIField
			}// endif get method
		}// end foreach

	}// end method

}
