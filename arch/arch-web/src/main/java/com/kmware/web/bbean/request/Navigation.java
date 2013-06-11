package com.kmware.web.bbean.request;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


@RequestScoped
@Named
public class Navigation {
	public static final String LIST = "list.jsf";
	public static final String NEW = "new.jsf";
	public static final String VIEW = "view.jsf";
	public static final String EDIT = "edit.jsf";
	public static final String COUNTRY="/pages/catalog/country/";
	public static final String COMPANY="/pages/catalog/company/";
	public static final String CITY="/pages/catalog/city/";
    public static final String TAX="/pages/catalog/tax/";
	public static final String FACES_REDIRECT_PARAM="faces-redirect=true";
	public static final String ID_PARAM = "id";
	public String redirectToCountries(){
		String res = COUNTRY+LIST+"?"+FACES_REDIRECT_PARAM;
		return res;
	}
	
	public String redirectToCities(){
		String res = CITY+LIST+"?"+FACES_REDIRECT_PARAM;
		return res;
	}
	
	public String redirectToCompanies(){
		String res = COMPANY+LIST+"?"+FACES_REDIRECT_PARAM;
		return res;
	}

    public String redirectToTaxes(){
        String res = TAX+LIST+"?"+FACES_REDIRECT_PARAM;
        return res;
    }

	
	public String toNew(){
		String uri = getRequest().getRequestURI();
		String res = NEW+"?"+FACES_REDIRECT_PARAM;
		return res;
	}

	public String toView(String id){
		String res = VIEW+"?"+ID_PARAM+"="+id+"&"+FACES_REDIRECT_PARAM;
		return res;
	}
	
	public String toEdit(String id){
		String res = EDIT+"?"+ID_PARAM+"="+id+"&"+FACES_REDIRECT_PARAM;
		return res;
	}
	
	public String toList(){
		String res = LIST+"?"+FACES_REDIRECT_PARAM;
		return res;
	}
	
	protected HttpServletRequest getRequest(){
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
}
