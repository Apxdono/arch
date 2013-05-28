package com.kmware.web.bbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.kmware.model.Subtopic;
import com.kmware.signleton.FieldCache.PageType;
import com.kmware.ui.processors.FormProcessor;
import com.kmware.ui.scaffold.FormFieldset;

@ManagedBean(name = SubtopicBean.BEAN_NAME)
@ViewScoped
public class SubtopicBean extends CommonCRUDBean<Subtopic> {
	private static final long serialVersionUID = -1638088498754486802L;
	public static final String BEAN_NAME = "subtopicBean";
	private static final String ENTITY_FIELD = "entity";

	@Override
	@PostConstruct
	public void init() {
		super.init();
	}

	@SuppressWarnings("unchecked")
	public List<FormFieldset> getFormFieldsets() {
		List<FormFieldset> formFieldsets = cache.getFields(SubtopicBean.BEAN_NAME, PageType.FORM);
		if (formFieldsets == null) {
			formFieldsets = (new FormProcessor(SubtopicBean.BEAN_NAME, SubtopicBean.ENTITY_FIELD)).getFormFieldsets(this.entity);
			cache.addFields(SubtopicBean.BEAN_NAME, PageType.FORM, formFieldsets);
		}
		return formFieldsets;
	}

	@Override
	protected void initEntity() {
		entity = new Subtopic();
	}

	public void saveSubtopic() {
		/*
		 * System.out.println("\r\nSaving entity. display name: " +
		 * entity.getDisplayName() + " Encoding: " + ((HttpServletRequest)
		 * FacesContext.getCurrentInstance() .getExternalContext().getRequest())
		 * .getCharacterEncoding() + "\r\n");
		 */
		dao.presist(entity);
		// FacesContext
		// .getCurrentInstance()
		// .getApplication()
		// .getNavigationHandler()
		// .handleNavigation(FacesContext.getCurrentInstance(), null,
		// "list.jsf?bitch-slap=true");
		redirectTo("list.jsf");
		// return "success";
	}

}
