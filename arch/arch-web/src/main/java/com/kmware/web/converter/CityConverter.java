package com.kmware.web.converter;

import com.kmware.dao.ICommonDAO;
import com.kmware.model.City;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/11/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */

@FacesConverter(value = "cityConverter",forClass = City.class)
public class CityConverter implements Converter{

    @EJB
    ICommonDAO dao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value!=null && value.trim().length() > 0){
            return dao.read(value,City.class);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof  City){
            return ((City) value).getId();
        }
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
