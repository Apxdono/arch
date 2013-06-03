package com.kmware.web.bbean.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.openfaces.component.filter.CompositeFilterCriterion;
import org.openfaces.component.filter.FilterCriterion;
import org.openfaces.component.filter.FilterCriterionProcessor;
import org.openfaces.component.table.Column;
import org.openfaces.util.Faces;

import com.kmware.dao.ICommonDAO;
import com.kmware.model.DBObject;

public class DataTableModel<T extends DBObject> implements Serializable {
	private static final long serialVersionUID = -5923542701195738827L;
	public static final String VAR_NAME_IN_PAGE = "item";
	protected ICommonDAO dao;
	protected Class<T> entityClass;
	protected FilterCriterionProcessor processor;
	protected Map<String, Object> parameters;

	public DataTableModel(ICommonDAO dao, Class<T> klazz) {
		this.dao = dao;
		this.entityClass = klazz;
		this.parameters = new HashMap<String, Object>();
		processor = new PGCriterionProcessor(this.parameters);

	}

	public List<T> getResultList() {
		Integer first = Faces.var("pageStart", Integer.class);
		Integer limit = Faces.var("pageSize", Integer.class);

		StringBuilder query = getQuery();
		query.append(buildPredicates());
		System.out.println("First:" + first + " Limit:" + limit);
		List<T> result = dao.getResultList(query.toString(), this.parameters,
				first != null ? first : 0, limit != null ? limit : 0,
				getOrderBy(), this.entityClass);
		this.parameters.clear();
		return result;
	}

	public int getResultCount() {
		StringBuilder query = new StringBuilder("SELECT COUNT(").append(
				VAR_NAME_IN_PAGE).append(") ");
		query.append(getQuery()).append(buildPredicates());
		int result = dao
				.getCount(query.toString(), this.parameters, this.entityClass);
		this.parameters.clear();
		return result; 
	}

	protected StringBuilder getQuery() {
		StringBuilder query = new StringBuilder();
		query.append("FROM ").append(entityClass.getCanonicalName())
				.append(" ").append(VAR_NAME_IN_PAGE);
		return query;
	}

	protected StringBuilder buildPredicates() {
		StringBuilder predicates = new StringBuilder();
		CompositeFilterCriterion filterCriteria = Faces.var("filterCriteria",
				CompositeFilterCriterion.class);
		if (filterCriteria != null && filterCriteria.getCriteria().size() > 0) {
			predicates.append(" WHERE ");
			for (FilterCriterion crit : filterCriteria.getCriteria()) {
				predicates.append(crit.process(this.processor)).append(" AND ");
			}
			predicates.delete(predicates.length() - 6, predicates.length() - 1);
		}

		return predicates;
	}

	protected String getOrderBy() {
		String sortColumn = Faces.var("sortColumnId", String.class);
		Boolean sortAscending = Faces.var("sortAscending", Boolean.class);

		if (sortColumn != null && !"".equals(sortColumn)) {

			Column col = (Column) findComponent(sortColumn);
			String expr = col.getSortingExpression().getExpressionString();
			String column = "";
			if (expr != null && !"".equals(expr)) {
				List<String> exprs = RegexpUtils.getElExpressions(expr);
				if (exprs.size() > 0) {
					column = exprs.get(0).substring(5, exprs.get(0).length() )+(sortAscending ? " ASC" : " DESC");
					return column;
				}
			}
		}
		return null;
	}

	private UIComponent findComponent(UIComponent root, String id) {

		UIComponent result = null;
		if (root.getId().equals(id))
			return root;

		for (UIComponent child : root.getChildren()) {
			if (child.getId().equals(id)) {
				result = child;
				break;
			}
			result = findComponent(child, id);
			if (result != null)
				break;
		}
		return result;
	}

	public UIComponent findComponent(String id) {

		UIComponent result = null;
		UIComponent root = FacesContext.getCurrentInstance().getViewRoot();
		if (root != null) {
			result = findComponent(root, id);
		}
		return result;

	}


}
