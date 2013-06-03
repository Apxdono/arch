package com.kmware.web.bbean.util;

import java.io.Serializable;

import org.openfaces.event.AjaxActionEvent;

public class TableState implements Serializable{
	private static final long serialVersionUID = -6414075674668948215L;	
	protected Integer pageIndex;
	protected Integer pageSize;
	
	public TableState(){
		pageIndex = 0;
		pageSize = 5;
	}
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public void pageSizeChangeListener(AjaxActionEvent event){
		
		System.out.println("value changed");
	}
	

}
