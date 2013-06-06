package com.kmware.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class FacesUtils {
	private static UIComponent findComponent(UIComponent root, String id) {

		UIComponent result = null;
		if (root.getId().equals(id))
			return root;

		for (UIComponent child : root.getChildren()) {
			if (child.getId().equals(id)) {
				result = child;
				break;
			}
			result = findComponent(child, id);
			if (result != null)
				break;
		}
		return result;
	}

	public static UIComponent findComponent(String id) {

		UIComponent result = null;
		UIComponent root = FacesContext.getCurrentInstance().getViewRoot();
		if (root != null) {
			result = findComponent(root, id);
		}
		return result;

	}

}
