package com.kmware.component.logical;

import javax.faces.component.UIComponentBase;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/13/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class PanelGridCell extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "com.kmware.component.logical.PanelGridCell";
    static final String COLSPAN = "colspan";

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public int getColspan(){
        Integer i = (Integer) getStateHelper().eval(COLSPAN);
        return i!=null? i.intValue():1;
    }

    public void setColspan(int colspan){
        getStateHelper().put(COLSPAN,colspan);
    }





}
