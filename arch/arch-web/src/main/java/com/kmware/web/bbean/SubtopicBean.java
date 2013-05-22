package com.kmware.web.bbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import javax.el.ValueExpression;

import com.kmware.model.Subtopic;
import com.kmware.ui.processors.FormProcessor;
import com.kmware.ui.scaffold.FormField;
import com.kmware.ui.scaffold.FormFieldset;

@ManagedBean(name = "subtopicBean")
@ViewScoped
public class SubtopicBean extends CommonCRUDBean<Subtopic> {
	private static final long serialVersionUID = -1638088498754486802L;

	private String test;
	private FormProcessor formProcessor;
	private List<FormFieldset> formFieldsets;

	@Override
	@PostConstruct
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		test = "alala";
		formProcessor = new FormProcessor("subtopicBean", "entity");
	}

	public List<FormFieldset> getFormFieldsets() {
		if (formFieldsets == null) {
			formFieldsets = formProcessor.getFormFieldsets(this.entity);
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

	public String saveSubtopic() {
		System.out.println("Saving entity. display name: "
				+ entity.getDisplayName()
				+ " Encoding: "
				+ ((HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest())
						.getCharacterEncoding());
		dao.presist(entity);
		return "list.jsf?faces-redirect=true";
	}

}
