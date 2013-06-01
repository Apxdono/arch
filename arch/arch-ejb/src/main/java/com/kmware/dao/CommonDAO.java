package com.kmware.dao;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

@Stateless
public class CommonDAO implements Serializable {
	private static final long serialVersionUID = 3862223808458181474L;
	
	@PersistenceContext
	private EntityManager em;
	
	public <E> E find(Class<E> klazz, Object id){
		return em.find(klazz, id);
	}
	
	public DAOMessage presist(Object o){
		em.persist(o);	
		return DAOMessage.OK;
	}
	
	public Object[] update(Object o){
		em.merge(o);
		return new Object[]{DAOMessage.OK,o};
	}
	
	public <T> List<T> getResultList(String ejbQL,Map<String, Object> params,int offset,int maxResults,Class<T> klazz){
		if(StringUtils.isBlank(ejbQL)){
			ejbQL = "FROM "+klazz.getSimpleName();
		}
		Query q = em.createQuery(ejbQL, klazz);
		
		if(params!=null && params.size() > 0){
			for (Map.Entry<String, Object> param : params.entrySet()) {
				q.setParameter(param.getKey(), param.getValue());
			}
		}
		
		if(offset > 0){
			q.setFirstResult(offset);
		}
		
		if(maxResults > 0){
			q.setMaxResults(maxResults);
		}
		
		return q.getResultList();
	}

}
