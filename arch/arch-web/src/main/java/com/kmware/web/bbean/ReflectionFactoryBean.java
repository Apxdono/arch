package com.kmware.web.bbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.kmware.ui.processors.FormProcessor;
import com.kmware.ui.scaffold.FormFieldset;

@Named
@ApplicationScoped
public class ReflectionFactoryBean implements Serializable {
	
	private Map<String, List<FormFieldset>> formSetsCache;
	
	@PostConstruct
	public void init(){
		formSetsCache = new HashMap<String, List<FormFieldset>>(10);
	}
	
	public List<FormFieldset> getFieldsets(String beanName,Object o){
		if(!formSetsCache.containsKey(beanName)){
			FormProcessor fp = new FormProcessor(beanName, "entity");
			synchronized (formSetsCache) {
				formSetsCache.put(beanName, fp.getFormFieldsets(o));
			}
		}
		return  new ArrayList<FormFieldset>(formSetsCache.get(beanName));
	}
}
