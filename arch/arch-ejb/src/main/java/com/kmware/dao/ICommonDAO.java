package com.kmware.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

public interface ICommonDAO extends Serializable {
	public static final String ORDER_BY_PARAM = "orderBy";
	public static final String GLOBAL_PARAM = "globalFilter";
	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";

	/**
	 * Save new entity in database
	 * 
	 * @param e
	 *            - entity
	 * @return e
	 */
	public abstract <E> DAOMessage create(E e);

	/**
	 * Find entity by id
	 * 
	 * @param id
	 *            of entity
	 * @param klass
	 *            class of entity
	 * @return entity if found or null
	 */
	public abstract <E> E read(Object id, Class<E> klass);

	/**
	 * Find entity by id and init it's lazy collections if needed
	 * 
	 * @see BasicDAO#read(Object)
	 * 
	 *      true if one needs to initialize the collections of the entity. false
	 *      otherwise
	 */
	public abstract <E> E readWithLazyObjects(Object id, Class<E> klass);

	/**
	 * Update entity in database
	 * 
	 * @param e
	 *            entity
	 * @return e
	 */
	public abstract <E> Object[] update(E e);

	/**
	 * Delete entity
	 * 
	 * @param t
	 * @param klass
	 */
	public abstract <E> void delete(E t, Class<E> klass);

	/**
	 * Get first entity from the result
	 * 
	 * @param query
	 * @param klass
	 * @return
	 */
	public abstract <E> E getSingleResult(String query, Class<E> klass);

	public abstract <E> E getSingleResult(String query,
			Map<String, Object> params, Class<E> klass);

	public abstract <E> E getSingleResult(String query,
			Map<String, Object> params, int first, int limit, Class<E> klass);

	public abstract <E> E getSingleResult(String query,
			Map<String, Object> params, int first, int limit, String orderBy,
			Class<E> klass);

	public abstract <E> List<E> getResultList(Class<E> klass);

	public abstract <E> List<E> getResultList(String query,
			Map<String, Object> params, Class<E> klass);

	public abstract <E> List<E> getResultList(String query,
			Map<String, Object> params, int first, int limit, Class<E> klass);

	public abstract <E> List<E> getResultList(String query,
			Map<String, Object> params, int first, int limit, String orderBy,
			Class<E> klass);

	public abstract int getCount(Class<?> klass);

	public abstract int getCount(String query, Map<String, Object> params,
			Class<?> klass);

	public abstract int getCount(String query, Map<String, Object> params,
			int first, int limit, Class<?> klass);

	public abstract int getCount(String query, Map<String, Object> params,
			int first, int limit, String orderBy, Class<?> klass);

	public abstract <E> Query getQuery(String query,
			Map<String, Object> params, Class<E> klass);

	public abstract <E> Query getQuery(String query,
			Map<String, Object> params, int first, int limit, String orderBy,
			Class<E> klass);

	public abstract TypedQuery<?> getNamedQuery(String queryName,
			Map<String, Object> params, Class<?> klass);

	public abstract TypedQuery<?> getNamedQuery(String queryName,
			Map<String, Object> params, int first, int limit, Class<?> klass);

	public abstract <E> List<E> getFromArray(Class<E> klazz, List<String> array,boolean inverse);
	
	public abstract <E> List<E> getNQResultList(String queryName, Class<E> klass);

	public abstract <E> List<E> getNQResultList(String queryName,
			Map<String, Object> params, Class<E> klass);

	public abstract <E> List<E> getNQResultList(String queryName,
			Map<String, Object> params, int first, int limit, Class<E> klass);

	public abstract <E> List<E> getNQResultList(String queryName,
			Map<String, Object> params, int first, int limit,
			String orderByField, String order, Class<E> klass);

	public abstract Long getNQCount(String queryName);

	public abstract Long getNQCount(String queryName, Map<String, Object> params);

	public abstract Long getNQCount(String queryName,
			Map<String, Object> params, int first, int limit);

}
