package com.kmware.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.kmware.model.DBObject;


@Stateless
@Local(ICommonDAO.class)
public class CommonDAO implements ICommonDAO{
	private static final long serialVersionUID = 3862223808458181474L;

	
	@PersistenceContext
	protected EntityManager em;

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#create(E)
	 */
	@Override
	public <E> DAOMessage create(E e) {
		em.persist(e);
		return DAOMessage.OK;
	}
	
	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#read(java.lang.Object, java.lang.Class)
	 */
	@Override
	public <E> E read(Object id, Class<E> klass) {
		
		return em.find(klass, id);
	}

	
	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#read(java.lang.Object, java.lang.Class, boolean)
	 */
	@Override
	public <E> E readWithLazyObjects(Object id, Class<E> klass) {
		E e = em.find(klass, id);
		if (e instanceof DBObject){
			((DBObject)e).loadLazyObjects();
		}
		return e;
	}

	
	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#update(E)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <E> Object[] update(E e) {
		e = em.merge(e);
		return new Object[]{DAOMessage.OK,e};
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#delete(E, java.lang.Class)
	 */
	@Override
	public <E> void delete(E t, Class<E> klass) {

	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getCount(java.lang.Class)
	 */
	@Override
	public int getCount(Class<?> klass) {

		return getQuery(null, null, 0, 0, null, klass).getFirstResult();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getCount(java.lang.String, java.util.Map, java.lang.Class)
	 */
	@Override
	public int getCount(String query, Map<String, Object> params, Class<?> klass) {

		return ((Long)getQuery(query, params, 0, 0, null, klass).getSingleResult()).intValue();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getCount(java.lang.String, java.util.Map, int, int, java.lang.Class)
	 */
	@Override
	public int getCount(String query, Map<String, Object> params, int first,
			int limit, Class<?> klass) {

		return getQuery(query, params, first, limit, null, klass)
				.getFirstResult();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getCount(java.lang.String, java.util.Map, int, int, java.lang.String, java.lang.Class)
	 */
	@Override
	public int getCount(String query, Map<String, Object> params, int first,
			int limit, String orderBy, Class<?> klass) {

		return getQuery(query, params, 0, 0, null, klass).getFirstResult();
	}

	@Override
	public TypedQuery<?> getNamedQuery(String queryName,
			Map<String, Object> params, Class<?> klass) {
		return this.getNamedQuery(queryName, params, 0, 0, klass);
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getNamedQuery(java.lang.String, java.util.Map, java.lang.Class)
	 */
	@Override
	public TypedQuery<?> getNamedQuery(String queryName,
			Map<String, Object> params, int first, int limit, Class<?> klass) {
		TypedQuery<?> query = em.createNamedQuery(queryName, klass);
		if (params != null) {
			
			for (String paramName : params.keySet()) {
					query.setParameter(paramName, params.get(paramName));
			}
		}
		
		if (first > 0) {
			query.setFirstResult(first);
		}
		if (limit > 0) {
			query.setMaxResults(limit);
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> getNQResultList(String queryName, Class<E> klass) {
		return (List<E>) getNamedQuery(queryName, null, klass).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> getNQResultList(String queryName, Map<String, Object> params,
			Class<E> klass) {
		return (List<E>) getNamedQuery(queryName, params, klass).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> getNQResultList(String queryName, Map<String, Object> params,
			int first, int limit, Class<E> klass) {

		return (List<E>) getNamedQuery(queryName, params, first, limit, klass)
				.getResultList();
	}
	

	@Override
	public <E> List<E> getNQResultList(String queryName,
			Map<String, Object> params, int first, int limit,
			String orderByField, String order, Class<E> klass) {
//		if(orderByField != null && !"".equals(orderByField)){
//			String queryString = em.createNamedQuery(queryName).unwrap(org.hibernate.Query.class).getQueryString();
//			return this.getResultList(queryString, params, first, limit, orderByField+" "+order, klass);
//		} else {
			return this.getNQResultList(queryName, params, first, limit, klass);
//		}
	}
	
	@Override
	public Long getNQCount(String queryName) {
		return (Long) getNamedQuery(queryName, null, Long.class).getSingleResult();
	}

	@Override
	public Long getNQCount(String queryName, Map<String, Object> params) {
		return (Long) getNamedQuery(queryName, params, Long.class).getSingleResult();
	}

	@Override
	public Long getNQCount(String queryName, Map<String, Object> params,
			int first, int limit) {

		return  (Long) getNamedQuery(queryName, params, first, limit, Long.class)
				.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getQuery(java.lang.String, java.util.Map, java.lang.Class)
	 */
	@Override
	public <E> Query getQuery(String query, Map<String, Object> params,
			Class<E> klass) {
		return getQuery(query, params, 0, 0, null, klass);
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getFromArray(Class<E> klazz, List<String> array,boolean inverse){
		String query = "SELECT DISTINCT(o) FROM "+klazz.getName()+" o WHERE o.id";
		if(inverse){
			query += " NOT IN (:array) ";
		} else {
			query += " IN (:array) ";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("array", array);		
		return getQuery(query, params, klazz).getResultList();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getQuery(java.lang.String, java.util.Map, int, int, java.lang.String, java.lang.Class)
	 */
	@Override
	public <E> Query getQuery(String query, Map<String, Object> params,
			int first, int limit, String orderBy, Class<E> klass) {
		if (query == null) {
			query = "FROM " + klass.getName();
		}
		if (orderBy != null) {
			query += " ORDER BY " + orderBy;
		}
		Query q = em.createQuery(query);
		if (params != null) {
			for (String paramName : params.keySet()) {
				q.setParameter(paramName, params.get(paramName));
			}
		}
		if (first > 0) {
			q.setFirstResult(first);
		}
		if (limit > 0) {
			q.setMaxResults(limit);
		}
		return q;
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getResultList(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> getResultList(Class<E> klass) {
		return getQuery(null, null, 0, 0, null, klass).getResultList();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getResultList(java.lang.String, java.util.Map, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> getResultList(String query, Map<String, Object> params,
			Class<E> klass) {

		return getQuery(query, params, 0, 0, null, klass).getResultList();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getResultList(java.lang.String, java.util.Map, int, int, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> getResultList(String query, Map<String, Object> params,
			int first, int limit, Class<E> klass) {

		return getQuery(query, params, first, limit, null, klass)
				.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getResultList(java.lang.String, java.util.Map, int, int, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> getResultList(String query, Map<String, Object> params,
			int first, int limit, String orderBy, Class<E> klass) {

		return getQuery(query, params, first, limit, orderBy, klass)
				.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getSingleResult(java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getSingleResult(String query, Class<E> klass) {
		return (E) getQuery(query, null, 0, 0, null, klass).getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getSingleResult(java.lang.String, java.util.Map, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getSingleResult(String query, Map<String, Object> params,
			Class<E> klass) {

		return (E) getQuery(query, params, 0, 0, null, klass).getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getSingleResult(java.lang.String, java.util.Map, int, int, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getSingleResult(String query, Map<String, Object> params,
			int first, int limit, Class<E> klass) {

		return (E) getQuery(query, params, first, limit, null, klass)
				.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.kmware.dao.IBasicDao#getSingleResult(java.lang.String, java.util.Map, int, int, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getSingleResult(String query, Map<String, Object> params,
			int first, int limit, String orderBy, Class<E> klass) {

		return (E) getQuery(query, params, first, limit, orderBy, klass)
				.getSingleResult();
	}
}
