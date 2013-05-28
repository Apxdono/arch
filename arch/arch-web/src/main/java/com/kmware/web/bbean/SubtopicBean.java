package com.kmware.web.bbean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import javax.el.ValueExpression;

import org.openfaces.util.Log;

import com.kmware.model.Subtopic;
import com.kmware.ui.processors.FormProcessor;
import com.kmware.ui.scaffold.FormField;
import com.kmware.ui.scaffold.FormFieldset;

@ManagedBean(name = "subtopicBean")
@ViewScoped
public class SubtopicBean extends CommonCRUDBean<Subtopic> {
	private static final long serialVersionUID = -1638088498754486802L;

	private FormProcessor formProcessor;
	private List<FormFieldset> formFieldsets;
	
	@Inject
	private ReflectionFactoryBean refBean;

	@Override
	@PostConstruct
	public void init() {
		// TODO Auto-generated method stub
		super.init();
//		formProcessor = new FormProcessor("subtopicBean", "entity");
	}

	public List<FormFieldset> getFormFieldsets() {
		if (formFieldsets == null) {
//			formFieldsets = (new FormProcessor("subtopicBean", "entity")).getFormFieldsets(this.entity);
			formFieldsets = refBean.getFieldsets("subtopicBean", this.entity);
		}
		return formFieldsets;
	}

	public void setFormFieldsets(List<FormFieldset> formFieldsets) {
		this.formFieldsets = formFieldsets;
	}

	@Override
	protected void initEntity() {
		entity = new Subtopic();
	}

	public void saveSubtopic() {
		/*System.out.println("\r\nSaving entity. display name: "
				+ entity.getDisplayName()
				+ " Encoding: "
				+ ((HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest())
						.getCharacterEncoding() + "\r\n");*/
		dao.presist(entity);
//		FacesContext
//				.getCurrentInstance()
//				.getApplication()
//				.getNavigationHandler()
//				.handleNavigation(FacesContext.getCurrentInstance(), null,
//						"list.jsf?bitch-slap=true");
		redirectTo("list.jsf");
		// return "success";
	}

	
}
