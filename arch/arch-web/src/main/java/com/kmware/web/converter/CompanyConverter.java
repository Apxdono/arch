package com.kmware.web.converter;

import com.kmware.dao.ICommonDAO;
import com.kmware.model.Company;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/11/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */

@Named("companyConverter")
@RequestScoped
public class CompanyConverter implements Converter{

    @EJB
    ICommonDAO dao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value!=null && value.trim().length() > 0){
            return dao.read(value,Company.class);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof  Company){
            return ((Company) value).getId();
        }
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
