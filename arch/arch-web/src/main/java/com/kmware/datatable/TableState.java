package com.kmware.datatable;

import java.io.Serializable;

import org.openfaces.event.AjaxActionEvent;

public class TableState implements Serializable{
	private static final long serialVersionUID = -6414075674668948215L;	
	protected Integer pageIndex;
	protected Integer pageSize;
	protected String sortColumnId;
	protected boolean sortAscending;
	
	public TableState(){
		pageIndex = 0;
		pageSize = 5;
		sortColumnId = "displayName";
		sortAscending = true;
	}
	
	public TableState(TableState st){
		pageIndex = st.getPageIndex().intValue();
		pageSize = st.getPageSize().intValue();
		sortColumnId = new String(st.getSortColumnId());
		sortAscending = st.isSortAscending();
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
	
	public String getSortColumnId() {
		return sortColumnId;
	}
	
	public void setSortColumnId(String sortColumnId) {
		this.sortColumnId = sortColumnId;
	}
	
	public void pageSizeChangeListener(AjaxActionEvent event){
		
		System.out.println("value changed");
	}
	
	public boolean isSortAscending() {
		return sortAscending;
	}
	
	public void setSortAscending(boolean sortAscending) {
		this.sortAscending = sortAscending;
	}

	@Override
	public String toString() {
		return "TableState [pageIndex=" + pageIndex + ", pageSize=" + pageSize
				+ ", sortColumnId=" + sortColumnId + ", sortAscending="
				+ sortAscending + "]";
	}

}
