package com.kmware.web.bbean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.kmware.model.Subtopic;

@ManagedBean(name="subtopicBean")
@ViewScoped
public class SubtopicBean extends CommonCRUDBean<Subtopic> {
	private static final long serialVersionUID = -1638088498754486802L;
	
	
	@Override
	@PostConstruct
	public void init() {
		// TODO Auto-generated method stub
		super.init();
	}


	@Override
	protected void initEntity() {
		entity = new Subtopic();
	}
}
