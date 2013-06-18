package com.kmware.component.panel;

import javax.faces.component.html.HtmlPanelGrid;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/18/13
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class PanelGrid extends HtmlPanelGrid {
    public static final String COMPONENT_FAMILY = "com.kmware.component.panel.PanelGrid";
    public static final String COMPONENT_TYPE = "com.kmware.component.panel.PanelGrid";

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
}
