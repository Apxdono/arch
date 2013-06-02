package com.kmware.web.bbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.kmware.dao.CommonDAO;
import com.kmware.dao.DAOMessage;
import com.kmware.model.DBObject;
import com.kmware.signleton.FieldCache;
import com.kmware.web.bbean.request.Navigation;
import com.kmware.web.converter.DefaultConverter;

public abstract class CommonCRUDBean<T extends DBObject> implements Serializable {
	private static final long serialVersionUID = -917866548312200765L;

	@EJB
	protected CommonDAO dao;
	@EJB
	protected FieldCache cache;
	@Inject
	protected Navigation nav;
	
	protected T entity;
	protected DefaultConverter defaultConverter;
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
		defaultConverter = new DefaultConverter();
		initEntity();
	}
	
	protected abstract void initEntity();

	public void save() {
		DAOMessage msg = dao.presist(entity);
		if (msg == DAOMessage.OK) {
			redirectTo(nav.toList());
		}
	}

	public void update() {
		Object[] result = dao.update(entity);
		DAOMessage msg = (DAOMessage) result[0];
		if (msg == DAOMessage.OK) {
			redirectTo("view.jsf");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getModel(){
		String query = "SELECT o FROM "+entity.getClass().getName()+" o ORDER BY o.displayName ASC";
		return (List<T>) dao.getResultList(query, null, 0, 0, entity.getClass());
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public DefaultConverter getDefaultConverter() {
		return defaultConverter;
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
