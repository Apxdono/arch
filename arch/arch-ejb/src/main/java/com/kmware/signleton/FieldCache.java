package com.kmware.signleton;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerService;


@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@SuppressWarnings("rawtypes")
public class FieldCache implements Serializable{
	private static final long serialVersionUID = 1582915385673369296L;
	
	private Map<String, List> fieldsCache;
	
	
	@PostConstruct
	public void initCache(){
		fieldsCache = new HashMap<String, List>(10);
	}
	
	@Lock(LockType.READ)
	public List getFields(String beanName,PageType type){
		String key = beanName+"@"+type.key;
		if(fieldsCache.containsKey(key)){
			return fieldsCache.get(key);
		}
		return null;
	}

	@Lock(LockType.WRITE)
	public void addFields(String beanName,PageType type,List fields){
		String key = beanName+"@"+type.key;
		synchronized (fieldsCache) {
			fieldsCache.put(key, fields);
		}		
	}
	
	@Schedule(minute="*",hour="*")
	@Lock(LockType.WRITE)
	public void resetCache(){
		System.out.println("Resetting cache!!!");
		System.out.println(fieldsCache.size());
		synchronized (fieldsCache) {
			fieldsCache.clear();
			System.out.println(fieldsCache.size());
		}
	}
	
	public static enum PageType{
		LIST("list"), VIEW("view"), FORM("form");
		public final String key;
		PageType(String k) {
			key = k;
		}
		
		@Override
		public String toString() {
			return key;
		}
	}
}
