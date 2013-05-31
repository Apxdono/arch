package com.kmware.ui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kmware.ui.constant.StringConstants;
import com.kmware.ui.enums.FormInputType;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.FIELD})
public @interface UIField {
	FormInputType type() default FormInputType.TEXT;
	String label() default "";
	int maxLength() default 255;
	String fieldset() default StringConstants.FIELDSET_DEFAULT_ID;
	String placeholder() default StringConstants.BOOLEAN_FALSE;
	String required() default StringConstants.BOOLEAN_FALSE;
	String showInView() default StringConstants.BOOLEAN_TRUE;
	String showInForm() default StringConstants.BOOLEAN_TRUE;
	Ajax[] eventHandlers() default {};
}
