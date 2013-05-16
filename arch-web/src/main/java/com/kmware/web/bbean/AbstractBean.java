package com.kmware.web.bbean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import com.kmware.dao.CommonDAO;

public abstract class AbstractBean implements Serializable {
	private static final long serialVersionUID = 3188138343276176218L;

	private boolean debug = true;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	@PostConstruct
	public void init(){
		log("Bean Initialized");
	}
	
	public void log(String message){
		if(debug){
			logger.info(message);
		}
	}
	
}
