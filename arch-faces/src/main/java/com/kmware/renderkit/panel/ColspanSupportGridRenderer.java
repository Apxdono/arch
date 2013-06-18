package com.kmware.renderkit.panel;

import com.kmware.component.logical.PanelGridCell;
import com.sun.faces.renderkit.html_basic.GridRenderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/12/13
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ColspanSupportGridRenderer extends GridRenderer {
    private static final String STYLE_CLASS_KEY = "styleClass";
    private static final String CLASS_KEY = "class";
    private static final String GRID_STYLE_CLASS = "form-table";
    private static final String GRID_CELL_STYLE_CLASS = "form-column";

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        String styleClass = (String) component.getAttributes().get(STYLE_CLASS_KEY);
        styleClass = ((styleClass == null)? GRID_STYLE_CLASS:styleClass+" "+GRID_STYLE_CLASS);
        component.getAttributes().put(STYLE_CLASS_KEY,styleClass);
        super.encodeBegin(context, component);
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {

        rendererParamsNotNull(context, component);

        if (!shouldEncodeChildren(component)) {
            return;
        }

        // Set up the variables we will need
        ResponseWriter writer = context.getResponseWriter();
        TableMetaInfo info = getMetaInfo(context, component);
        int columnCount = info.columns.size();
        boolean open = false;
        int i = 0;

        // Render our children, starting a new row as needed
        renderTableBodyStart(context, component, writer);
        boolean rowRendered = false;
        for (Iterator<UIComponent> kids = getChildren(component);
             kids.hasNext();) {

            UIComponent child = kids.next();
            if (!child.isRendered()) {
                continue;
            }
            if ((i % columnCount) == 0) {
                if (open) {
                    renderRowEnd(context, component, writer);
                }
                renderRowStart(context, component, writer);
                rowRendered = true;
                open = true;
                info.newRow();
            }

            if(isColspanNeeded(child)){
                PanelGridCell cell = (PanelGridCell) child;
                cell.setColspan(columnCount - (i % columnCount));
                renderRow(context, component, cell, writer);
                i=0;
            }  else {
                renderRow(context, component, child, writer);
                i++;
            }
        }
        if (open) {
            renderRowEnd(context, component, writer);
        }
        if (!rowRendered) {
            this.renderEmptyTableRow(writer, component);
        }
        renderTableBodyEnd(context, component, writer);
    }

    @Override
    protected void renderRow(FacesContext context,
                             UIComponent table,
                             UIComponent child,
                             ResponseWriter writer)
            throws IOException {

        TableMetaInfo info = getMetaInfo(context, table);
        writer.startElement("td", table);
        String columnClass = info.getCurrentColumnClass();
        columnClass = (columnClass == null) ? GRID_CELL_STYLE_CLASS :columnClass + " "+GRID_CELL_STYLE_CLASS;
        writer.writeAttribute(CLASS_KEY, columnClass, "columns");
        encodeRecursive(context, child);
        writer.endElement("td");
        writer.writeText("\n", table, null);

    }

    private void renderEmptyTableRow(final ResponseWriter writer,
                                     final UIComponent component) throws IOException {
        writer.startElement("tr", component);
        writer.startElement("td", component);
        writer.endElement("td");
        writer.endElement("tr");
    }

    private boolean isColspanNeeded(UIComponent child){
        if(child instanceof PanelGridCell){
            return true;
        }
        return false;
    }

}
