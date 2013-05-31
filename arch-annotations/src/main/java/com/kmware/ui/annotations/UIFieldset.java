package com.kmware.ui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kmware.ui.constant.StringConstants;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
public @interface UIFieldset {
	public int position() default 0;
	public String id() default StringConstants.FIELDSET_DEFAULT_ID;
	public String label() default StringConstants.FIELDSET_DEFAULT_NAME;
}
