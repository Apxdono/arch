package com.kmware.web.bbean;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.kmware.dao.CommonDAO;
import com.kmware.dao.DAOMessage;
import com.kmware.model.DBObject;

public abstract class CommonCRUDBean<T extends DBObject> implements Serializable {
	private static final long serialVersionUID = -917866548312200765L;

	@EJB
	protected CommonDAO dao;
	protected T entity;
	private boolean debug = true;
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

	public String save() {
		DAOMessage msg = dao.presist(entity);
		if (msg != DAOMessage.OK) {

			return "";
		}
		return "list.jsf?faces-redirect=true";
	}

	public String update() {
		Object[] result = dao.update(entity);
		DAOMessage msg = (DAOMessage) result[0];
		if (msg != DAOMessage.OK) {

			return "";
		}
		return "";
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	public int fieldLength(String fieldName){
		if(StringUtils.isNotBlank(fieldName)){
			Method m = null;
			try {
				m = entity.getClass().getMethod("get"+WordUtils.capitalize(fieldName), new Class<?>[]{});
			} catch (Exception e) {

			}
			if(m!=null && m.isAnnotationPresent(Column.class)){
				Column annot = m.getAnnotation(Column.class);
				return annot.length();
			}
		}		
		return 255;
	}

}
