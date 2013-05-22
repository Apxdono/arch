package com.kmware.ui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
public @interface UIFieldset {
	public static final String COMMON_ID = "common";
	public static final String COMMON_LABEL = "baseName";
	public int position() default 0;
	public String id() default COMMON_ID;
	public String label() default COMMON_LABEL;
}
