package com.kmware.ui.annotations;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/20/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
@Inherited
public @interface ViewField {
}
