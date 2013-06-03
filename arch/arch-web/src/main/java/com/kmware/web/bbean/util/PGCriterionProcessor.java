package com.kmware.web.bbean.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openfaces.component.filter.AndFilterCriterion;
import org.openfaces.component.filter.ExpressionFilterCriterion;
import org.openfaces.component.filter.FilterCondition;
import org.openfaces.component.filter.FilterCriterionProcessor;
import org.openfaces.component.filter.OrFilterCriterion;


public class PGCriterionProcessor extends FilterCriterionProcessor implements
		Serializable {

	private static final long serialVersionUID = 8799406847266388195L;
	private Map<String, Object> parameters;

	public PGCriterionProcessor() {
		parameters = new HashMap<String, Object>();
	}

	public PGCriterionProcessor(Map<String, Object> parametersRef) {
		this.parameters = parametersRef;
	}

	@Override
	public Object process(ExpressionFilterCriterion criterion) {

		List<String> columns = RegexpUtils.getElExpressions(criterion
				.getExpressionStr());
		StringBuilder sb = new StringBuilder();
		if (columns.size() == 1) {
			String column = columns.get(0);
			System.out.println("Column: " + column + " VALUE: "
					+ criterion.getArg1());
			if (column != null && !"".equals(column)) {
				String parameterName = column.replace(".", "");
				buildForCondition(criterion, column, parameterName, sb);
			}

		} else if (columns.size() > 1) {
			sb.append(" (");
			for (int i = 0, len = columns.size(); i < len; i++) {
				String column = columns.get(i);
				String parameterName = column.replace(".", "");
				if (column.contains(".toString")) {
					column = column.substring(0, column.length() - 9);
					parameterName = column.replace(".", "");
					column = "CAST(" + column + " AS string)";
				}

				buildForCondition(criterion, column, parameterName, sb);
				if (i != len - 1) {
					sb.append(" OR ");
				}
			}
			sb.append(") ");
		}
		return sb.toString();
	}

	@Override
	public Object process(AndFilterCriterion criterion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object process(OrFilterCriterion criterion) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	@SuppressWarnings("unused")
	protected void buildForCondition(ExpressionFilterCriterion criterion,
			String column, String parameterName, StringBuilder sb) {
		Object value1 = criterion.getArg1();
		Object value2 = criterion.getArg2();
		FilterCondition condition = criterion.getCondition();
		if ((condition == FilterCondition.BEGINS_WITH
				|| condition == FilterCondition.CONTAINS || condition == FilterCondition.ENDS_WITH)
				&& !criterion.isCaseSensitive()) {
			column = " LOWER(" + column + ") ";
			value1 = value1.toString().toLowerCase();
		}
		parameters.put(parameterName, value1);
		switch (condition) {
		case CONTAINS:
			sb.append(column).append(" LIKE :").append(parameterName)
					.append(" ");
			parameters.put(parameterName, "%" + value1 + "%");

			break;
		case BEGINS_WITH:
			sb.append(column).append(" LIKE :").append(parameterName)
					.append(" ");
			parameters.put(parameterName, value1 + "%");
			break;
		case ENDS_WITH:
			sb.append(column).append(" LIKE :").append(parameterName)
					.append(" ");
			parameters.put(parameterName, "%" + value1);
			break;
		case EQUALS:
			sb.append(column).append(" = :").append(parameterName).append(" ");
			break;
		case GREATER:
			sb.append(column).append(" > :").append(parameterName).append(" ");
			break;
		case GREATER_OR_EQUAL:
			sb.append(column).append(" >= :").append(parameterName).append(" ");
			break;
		case BETWEEN:
			break;
		case EMPTY:
			sb.append(column).append(" is NULL ");
			parameters.remove(parameterName);
			break;
		case LESS:
			sb.append(column).append(" < :").append(parameterName).append(" ");
			break;
		case LESS_OR_EQUAL:
			sb.append(column).append(" <= :").append(parameterName).append(" ");
			break;
		default:
			break;
		}
	}

}