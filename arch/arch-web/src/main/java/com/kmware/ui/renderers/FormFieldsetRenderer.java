package com.kmware.ui.renderers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlMessage;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.kmware.ui.component.UIFormFieldset;
import com.kmware.ui.scaffold.FormField;
import com.kmware.ui.scaffold.FormFieldset;

@FacesRenderer(componentFamily = UIFormFieldset.COMPONENT_FAMILY, rendererType = FormFieldsetRenderer.RENDERER_TYPE)
public class FormFieldsetRenderer extends Renderer {

	public static final String RENDERER_TYPE = "com.kmware.FormFieldsetRenderer";
	
	
	@Override
	public boolean getRendersChildren() {
		return true;
	}
	
	
	@Override
	public void decode(FacesContext context, UIComponent component) {
		super.decode(context, component);
		for (UIComponent kid : component.getChildren()) {
			kid.decode(context);
		}
		
	}
	
	// Render the element
	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		super.encodeBegin(context, component);

		if (!context.isValidationFailed()) {
			ResponseWriter writer = context.getResponseWriter();
			UIFormFieldset fs = (UIFormFieldset) component;

			List<FormFieldset> values = fs.getFieldsets();

			for (FormFieldset val : values) {

				writer.startElement("div", component);
				String id = val.getId();
				writer.writeAttribute("id", id, "id");

				HtmlPanelGrid grid = new HtmlPanelGrid();
				grid.setId(val.getId() + "grid");
				grid.setColumns(3);
				component.getChildren().add(grid);

				for (FormField field : val.getFields()) {
					processChildField(field, grid);
				}
				
				encodeChildren(context, component);

				writer.endElement("div");
			}
		}
	}

	// Process each visible field
	public void processChildField(FormField field, UIComponent component) {
		UIComponent input = null;
		if (String.class.equals(field.getReturnType())) {
			input = getTextInput(field, component);
		} else if (Boolean.class.equals(field.getReturnType())) {
			input = getChechbox(field, component);
		}
		if (input != null) {
			UIComponent label = getLabel(field, input);
			component.getChildren().add(label);
			component.getChildren().add(input);
			if (field.isRequired()) {
				HtmlMessage msg = new HtmlMessage();
				msg.setId(field.getFieldName() + "msg");
				msg.setFor(input.getClientId());
				component.getChildren().add(msg);
			} else {
				HtmlOutputText txt = new HtmlOutputText();
				txt.setId(null);
				component.getChildren().add(txt);
			}
		}
	}

	// Define and get the required text input
	public UIComponent getTextInput(FormField field, UIComponent parent) {
		UIComponent result = null;
		if (field.isPlaceholder()) {

		} else {
			HtmlInputText input = new HtmlInputText();
			input.setId(field.getFieldName() + "input");
			input.setValueExpression("value", field.getValue());
			input.setLabel(field.getLabel());
			input.setRequired(field.isRequired());
			input.setMaxlength(field.getMaxLength());
			result = input;
		}
		return result;
	}

	// Define and get the required text input
	public UIComponent getChechbox(FormField field, UIComponent parent) {
		UIComponent result = null;
		HtmlSelectBooleanCheckbox input = new HtmlSelectBooleanCheckbox();
		input.setId(field.getFieldName() + "chkbx");
		input.setValueExpression("value", field.getValue());
		input.setLabel(field.getLabel());
		result = input;
		return result;
	}

	// Get Label for input
	public UIComponent getLabel(FormField field, UIComponent input) {
		HtmlOutputLabel label = new HtmlOutputLabel();
		label.setId(field.getFieldName() + "label");
		label.setFor(input.getId());
		label.setValue(field.getLabel());
		return label;
	}

}
