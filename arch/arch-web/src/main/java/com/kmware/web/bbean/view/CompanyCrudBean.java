package com.kmware.web.bbean.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.kmware.model.Company;
import com.kmware.web.bbean.CommonCRUDBean;

@ViewScoped
@ManagedBean(name="companyCRUD")
public class CompanyCrudBean extends CommonCRUDBean<Company> {
	private static final long serialVersionUID = -9022037695214476125L;
}
