package com.kmware.tag.logical;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/13/13
 * Time: 5:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PanelGridCellTagHandler extends TagHandler {

    public PanelGridCellTagHandler(TagConfig config) {
        super(config);
    }

    @Override
    public void apply(FaceletContext ctx, UIComponent parent) throws IOException {

    }
}
