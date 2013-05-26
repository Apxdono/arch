package com.kmware.ui.renderers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
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

	public static ELContext getELContext() {
		return FacesContext.getCurrentInstance().getELContext();
	}

	public static ExpressionFactory getExpressionFactory() {
		return getApplication().getExpressionFactory();
	}

	public static Application getApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}

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

		if (!context.isValidationFailed() || !context.isPostback()) {
			ResponseWriter writer = context.getResponseWriter();
			UIFormFieldset fs = (UIFormFieldset) component;

			List<FormFieldset> values = fs.getFieldsets();

			for (FormFieldset val : values) {

				writer.startElement("div", component);
				String id = val.getId();
				writer.writeAttribute("id", id, "id");

				HtmlPanelGrid grid = (HtmlPanelGrid) getApplication().createComponent(HtmlPanelGrid.COMPONENT_TYPE);
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
				HtmlMessage msg = (HtmlMessage) getApplication()
						.createComponent(HtmlMessage.COMPONENT_TYPE);
				msg.setId(field.getFieldName() + "msg");
				msg.setFor(input.getClientId());
				component.getChildren().add(msg);
			} else {
				HtmlOutputText txt = (HtmlOutputText) getApplication()
						.createComponent(HtmlOutputText.COMPONENT_TYPE);
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
			HtmlInputText input = (HtmlInputText) getApplication()
					.createComponent(HtmlInputText.COMPONENT_TYPE);
			input.setId(field.getFieldName() + "input");
 			final ValueExpression ve = field.getValue(); 
			input.setValueExpression("value", ve);
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
		HtmlSelectBooleanCheckbox input = (HtmlSelectBooleanCheckbox) getApplication()
				.createComponent(HtmlSelectBooleanCheckbox.COMPONENT_TYPE);
		input.setId(field.getFieldName() + "chkbx");
		final ValueExpression ve = field.getValue(); 
		input.setValueExpression("value", ve);
		input.setLabel(field.getLabel());
		result = input;
		return result;
	}

	// Get Label for input
	public UIComponent getLabel(FormField field, UIComponent input) {
		HtmlOutputLabel label = (HtmlOutputLabel) getApplication()
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		label.setId(field.getFieldName() + "label");
		label.setFor(input.getId());
		label.setValue(field.getLabel());
		return label;
	}

}
