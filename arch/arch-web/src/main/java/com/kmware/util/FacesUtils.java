package com.kmware.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.kmware.model.DBObject;

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
	
	public static <T extends DBObject> SelectItem[] getAsSelectItems(List<T> list){
		if(list==null) return new SelectItem[0];
		
		SelectItem[] items = new SelectItem[list.size()];
		
		for(int i=0,len=list.size();i<len;i++){
			SelectItem item = new SelectItem();
			item.setValue(list.get(i).getId());
			item.setLabel(list.get(i).getDisplayName());
			items[i] = item;
		}
		return items;
	}
	
	public static <T extends DBObject> List<String> pluckIds(List<T> items){
		if(items == null) return new ArrayList<String>(0);
		List<String> ids = new ArrayList<String>(items.size());
		for(int i=0, len = items.size(); i<len; i++){
			ids.add(items.get(i).getId());
		}
		return ids;
	}

}
