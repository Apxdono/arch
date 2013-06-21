package com.kmware.ui.annotations;

import com.kmware.ui.constant.StringConstants;
import com.kmware.ui.enums.FormInputType;

import java.lang.annotation.*;

import static com.kmware.ui.constant.StringConstants.*;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/20/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
@Inherited
public @interface EditField {
    FormInputType type() default FormInputType.TEXT;
    String label() default "";
    String value() default "";
    String groupId() default DEFAULT_UI_GROUP_ID;
    String required() default BOOLEAN_FALSE;
    int maxLength() default 255;
    boolean placeholder() default false;
    String placeholderText() default "";
    String rendered() default BOOLEAN_TRUE;
    String values() default "";
    String var() default JSF_VAR;
    String itemLabel() default SELECT_ITEM_LABEL;
    String itemValue() default SELECT_ITEM_VALUE;
    String converter() default "";
}
