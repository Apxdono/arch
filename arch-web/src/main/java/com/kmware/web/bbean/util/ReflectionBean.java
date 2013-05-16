package com.kmware.web.bbean.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.kmware.jsf.annotation.UIEntity;
import com.kmware.jsf.annotation.UIField;

@Named("reflUtls")
@RequestScoped
public class ReflectionBean {

	public int fieldLength(Object o, String fieldName) {
		System.out.println("FUUCK");
		if (StringUtils.isNotBlank(fieldName)) {
			Column annot = this.getAnnotation(o, fieldName, Column.class);
			return annot != null ? annot.length() : 255;
		}

		return 255;
	}

	public int maxlength(UIComponent component) {
		if (component != null) {
				String el = component.getValueExpression("value")
						.getExpressionString();
				String plain = el.substring(2, el.length() - 1);
				int lastDot = plain.lastIndexOf('.');
				if (lastDot != -1) {
					String field = plain.substring(lastDot + 1);
					String entity = "#{" + plain.substring(0, lastDot) + "}";
					Column annot = this.getAnnotation(
							resolveExpression(entity), field, Column.class);
					return annot != null ? annot.length() : 255;
				}
			}
		return 255;
	}

	public String label(Object o, String fieldName) {
		if (o != null && StringUtils.isNotBlank(fieldName)) {
			String resourceBundleName = this.getAnnotation(o, null,
					UIEntity.class).messageBundle();
			String key = this.getAnnotation(o, fieldName, UIField.class)
					.label();
			ResourceBundle bundle = FacesContext
					.getCurrentInstance()
					.getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(),
							resourceBundleName);
			return bundle.getString(key);
		}
		return "";
	}

	public Object resolveExpression(String el) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		ExpressionFactory elr = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory();
		return elr.createValueExpression(elContext, el, Object.class).getValue(elContext);
	}

	public <T extends Annotation> T getAnnotation(Object o, String field,
			Class<T> annotationClass) {
		if (field == null || StringUtils.isBlank(field)) {
			if (o.getClass().isAnnotationPresent(annotationClass)) {
				T annot = o.getClass().getAnnotation(annotationClass);
				return annot;
			}
		} else {
			try {
				Method m = o.getClass().getMethod(
						"get" + WordUtils.capitalize(field), new Class<?>[] {});
				if (m.isAnnotationPresent(annotationClass)) {
					T annot = m.getAnnotation(annotationClass);
					return annot;
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
