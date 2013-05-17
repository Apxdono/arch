package com.kmware.web.bbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.el.ValueExpression;

import com.kmware.model.Subtopic;
import com.kmware.ui.processors.FormProcessor;
import com.kmware.ui.scaffold.FormField;

@ManagedBean(name="subtopicBean")
@ViewScoped
public class SubtopicBean extends CommonCRUDBean<Subtopic> {
	private static final long serialVersionUID = -1638088498754486802L;
	
	private String test;
	private FormProcessor formProcessor;
	
	
	@Override
	@PostConstruct
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		test = "alala";
		formProcessor = new FormProcessor("subtopicBean", "entity", FacesContext.getCurrentInstance());
	}
	
	public List<FormField> getFormFields(){
		return formProcessor.getVisibleFields(this.entity);
	}


	@Override
	protected void initEntity() {
		entity = new Subtopic();
	}
	
	public Object getSomeEL(){
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		ValueExpression valExpr = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(elContext, "#{subtopicBean.test}", String.class);
		return valExpr.getValue(elContext);
	}
	
	public void setSomeEL(Object o){
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		ValueExpression valExpr = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(elContext, "#{subtopicBean.test}", String.class);
		valExpr.setValue(elContext, o);
		System.out.println("Holy molly");
	}
	
	public String getTest() {
		return test;
	}
	
	public void setTest(String test) {
		this.test = test;
		System.out.println(test);
	}
	
	public ELContext getELContext(){
		return FacesContext.getCurrentInstance().getELContext();
	}
}
