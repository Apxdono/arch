package com.kmware.web.bbean.session;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.kmware.web.bbean.util.TableState;

@Named
@SessionScoped
public class SessionBean implements Serializable{
	private static final long serialVersionUID = -984184186102386740L;
	
	private ConcurrentHashMap<Object, Object> cache;
	
	@PostConstruct
	public void init(){
		cache = new ConcurrentHashMap<Object, Object>();
	}
	
	public void put(Object key, Object value){
		cache.put(key, value);
	}
	
	public Object get(Object key){
		if(cache.contains(key)){
			return cache.get(key);
		}
		return null;
	}
	
	public TableState getTableState(Object key){
		if(cache.containsKey(key)){
			return (TableState) cache.get(key);
		}
				
		return new TableState();
	}
}
