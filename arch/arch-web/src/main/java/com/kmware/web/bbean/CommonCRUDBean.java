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

import com.kmware.dao.DAOMessage;
import com.kmware.dao.ICommonDAO;
import com.kmware.model.DBObject;
import com.kmware.signleton.FieldCache;
import com.kmware.web.bbean.request.Navigation;
import com.kmware.web.bbean.util.DataTableModel;
import com.kmware.web.bbean.util.TableState;
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
		log("Dao injected : " + (dao != null));
		defaultConverter = new DefaultConverter();
		entityClass = ((Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]);
		entity = entityClass.newInstance();
	}

	public void save() {
		DAOMessage msg = dao.create(entity);
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

	protected DataTableModel<T> getModel() {
		if (model == null) {
			model = new DataTableModel<T>(dao, entityClass);

		}
		return model;
	}

	public List<T> getItems() {
		return getModel().getResultList();
	}

	public int getItemsTotalCount() {
		return getModel().getResultCount();
	}

	public TableState getTableState() {
		if (tableState == null) {
			tableState = new TableState();
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
	 * Get the default entity converter for DBObject.class (uses id and displayName fields)
	 * @return instance of a converter
	 */
	public DefaultConverter getDefaultConverter() {
		return defaultConverter;
	}

	/**
	 * Perform redirect 
	 * @param url - where to go
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

}
