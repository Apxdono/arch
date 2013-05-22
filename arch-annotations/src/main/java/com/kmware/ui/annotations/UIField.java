package com.kmware.ui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.FIELD})
public @interface UIField {
	String label() default "";
	String fieldset() default UIFieldset.COMMON_ID;
	boolean placeholder() default false;
	boolean required() default false;
	boolean showInList() default true;
	boolean showInView() default true;
	boolean showInForm() default true;
	int maxLength() default 255;
}
