package com.kmware.datatable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openfaces.component.filter.CompositeFilterCriterion;
import org.openfaces.component.filter.FilterCriterion;
import org.openfaces.component.filter.FilterCriterionProcessor;
import org.openfaces.component.table.Column;
import org.openfaces.util.Faces;

import com.kmware.dao.ICommonDAO;
import com.kmware.model.DBObject;
import com.kmware.util.FacesUtils;
import com.kmware.util.RegexpUtils;

public class DataTableModel<T extends DBObject> implements Serializable {
	private static final long serialVersionUID = -5923542701195738827L;
	public static final String VAR_NAME_IN_PAGE = "item";
	protected ICommonDAO dao;
	protected Class<T> entityClass;
	protected FilterCriterionProcessor processor;
	protected Map<String, Object> parameters;
	protected TableState state;
	
	public DataTableModel(ICommonDAO dao,TableState state,Class<T> klazz) {
		this.dao = dao;
		this.entityClass = klazz;
		this.parameters = new HashMap<String, Object>();
		this.processor = new PGCriterionProcessor(this.parameters);
		this.state = state;
	}

	public List<T> getResultList() {
		Integer first = Faces.var("pageStart", Integer.class);
		Integer limit = Faces.var("pageSize", Integer.class);
		
		StringBuilder query = getQuery();
		query.append(buildPredicates());
		List<T> result = dao.getResultList(query.toString(), this.parameters,
				first != null ? first : 0, limit != null ? limit : 0,
				getOrderBy(), this.entityClass);
		this.parameters.clear();
		int newOffset = first/state.getPageSize();
		if(state.getPageIndex()!= newOffset){
			state.setPageIndex(newOffset);
		}
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

			Column col = (Column) FacesUtils.findComponent(sortColumn);
			String expr = col.getSortingExpression().getExpressionString();
			String column = "";
			if (expr != null && !"".equals(expr)) {
				List<String> exprs = RegexpUtils.getElExpressions(expr);
				if (exprs.size() > 0) {
					String buf = exprs.get(0).substring(5, exprs.get(0).length());
					column = VAR_NAME_IN_PAGE +"."+buf+(sortAscending ? " ASC" : " DESC");
					if(state.getSortColumnId()!=null && !state.getSortColumnId().equals(buf)){
						state.setSortColumnId(buf);
						state.setSortAscending(sortAscending);
					}
					return column;
				}
			}
		}
		return null;
	}
	

}
