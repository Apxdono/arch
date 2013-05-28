package com.kmware.web.bbean;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.kmware.dao.CommonDAO;
import com.kmware.dao.DAOMessage;
import com.kmware.model.DBObject;
import com.kmware.signleton.FieldCache;

public abstract class CommonCRUDBean<T extends DBObject> implements Serializable {
	private static final long serialVersionUID = -917866548312200765L;

	@EJB
	protected CommonDAO dao;
	@EJB
	protected FieldCache cache;
	
	protected T entity;
	private boolean debug = false;
	private Logger logger = Logger.getLogger(getClass().getName());
		
	public void log(String message){
		if(debug){
			logger.info(message);
		}
	}

	@PostConstruct
	public void init() {
		log("Dao injected : " + (dao != null));
		initEntity();
	}
	
	protected abstract void initEntity();

	public void save() {
		DAOMessage msg = dao.presist(entity);
		if (msg != DAOMessage.OK) {
			redirectTo("list.jsf");
		}
	}

	public void update() {
		Object[] result = dao.update(entity);
		DAOMessage msg = (DAOMessage) result[0];
		if (msg != DAOMessage.OK) {
			redirectTo("view.jsf");
		}
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	//Used for redirects. Best I came up with to fight the form-resubmission problem
	//and empty data submissions. Looks like a valid PRG concept so should be okay 
	protected void redirectTo(String url){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			this.log("SOMETHING HAPPENED AND I CAN'T GO TO:"+url+" . I'M REALLY SORRY :(");
			this.log("See the stacktrace below maybe it will be helpful");
			e.printStackTrace();
		}
	}

}
