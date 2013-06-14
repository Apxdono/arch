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

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        String styleClass = (String) component.getAttributes().get("styleClass");
        styleClass = ((styleClass == null)? "form-table":styleClass+" form-table");
        component.getAttributes().put("styleClass",styleClass);
        super.encodeBegin(context, component);    //To change body of overridden methods use File | Settings | File Templates.
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
            renderRow(context, component, child, writer);
            if(this.isColspanNeeded(child)){
                int colspan = ((PanelGridCell)child).getColspan();
                if(i+colspan % columnCount > 0){
                    renderRowEnd(context,component,writer);
                    renderRowStart(context,component,writer);
                    i = 0;
                }
                i+=colspan;
            } else {
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
        if(this.isColspanNeeded(child)){
                int cols = ((PanelGridCell)child).getColspan();
                writer.writeAttribute("colspan",cols,null);
        }
        String columnClass = info.getCurrentColumnClass();
        //TODO make static field
        columnClass = (columnClass == null) ? " form-column ":columnClass + " form-column ";
        writer.writeAttribute("class", columnClass, "columns");
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
