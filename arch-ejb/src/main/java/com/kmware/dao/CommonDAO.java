package com.kmware.dao;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	

}
