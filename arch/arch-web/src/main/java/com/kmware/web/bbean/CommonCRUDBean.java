package com.kmware.web.bbean;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.kmware.dao.DAOMessage;
import com.kmware.dao.ICommonDAO;
import com.kmware.datatable.DataTableModel;
import com.kmware.datatable.TableState;
import com.kmware.model.DBObject;
import com.kmware.signleton.FieldCache;
import com.kmware.web.bbean.request.Navigation;
import com.kmware.web.bbean.session.SessionBean;
import com.kmware.web.converter.DefaultConverter;

public abstract class CommonCRUDBean<T extends DBObject> implements
		Serializable {
	private static final long serialVersionUID = -917866548312200765L;
	
	@EJB
	protected ICommonDAO dao;
	@EJB
	protected FieldCache cache;
	@Inject
	protected Navigation nav;
	@Inject
	protected SessionBean sessionBean;

	protected T entity;
	protected Class<T> entityClass;
	protected DefaultConverter defaultConverter;
	private boolean debug = false;
	private Logger logger = Logger.getLogger(getClass().getName());
	protected TableState tableState;
	protected DataTableModel<T> model;

	public void log(String message) {
		if (debug) {
			logger.info(message);
		}
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() throws InstantiationException, IllegalAccessException {
		defaultConverter = new DefaultConverter();
		entityClass = ((Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]);
		String id = getParameter(Navigation.ID_PARAM);
		if(StringUtils.isNotBlank(id)){
			entity = dao.readWithLazyObjects(id, entityClass);
		} else {
			entity = entityClass.newInstance();
		}
	}
	
	protected void preSave(){
		
	}
	
	protected void preUpdate(){
		
	}
	

	public void save() {
        preSave();
        DAOMessage msg = dao.create(entity);
		if (msg == DAOMessage.OK) {
			redirectTo(nav.toList());
		}
	}

	public void update() {
		preUpdate();
		Object[] result = dao.update(entity);
		DAOMessage msg = (DAOMessage) result[0];
		if (msg == DAOMessage.OK) {
			redirectTo(nav.toView(entity.getId()));
		}
	}

	protected DataTableModel<T> getModel() {
		if (model == null) {
			model = new DataTableModel<T>(dao, getTableState(), entityClass);

		}
		return model;
	}

	public List<T> getItems() {
		List<T> res = getModel().getResultList();
		sessionBean.put(FacesContext.getCurrentInstance().getViewRoot()
				.getViewId(), tableState);
		return res;
	}

	public int getItemsTotalCount() {
		return getModel().getResultCount();
	}

	public TableState getTableState() {
		if (tableState == null) {
			tableState = new TableState(sessionBean.getTableState(FacesContext
					.getCurrentInstance().getViewRoot().getViewId()));
		}
		return tableState;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	/**
	 * Get the default entity converter for DBObject.class (uses id and
	 * displayName fields)
	 * 
	 * @return instance of a converter
	 */
	public DefaultConverter getDefaultConverter() {
		return defaultConverter;
	}

	/**
	 * Perform redirect
	 * 
	 * @param url
	 *            - where to go
	 */
	protected void redirectTo(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(url);
		} catch (IOException e) {
			this.log("SOMETHING HAPPENED AND I CAN'T GO TO:" + url
					+ " . I'M REALLY SORRY :(");
			this.log("See the stacktrace below maybe it will be helpful");
			e.printStackTrace();
		}
	}

	
	protected String getParameter(String key){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
	}
}
