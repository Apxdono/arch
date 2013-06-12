package com.kmware.web.bbean.view;

import com.kmware.model.City;
import com.kmware.model.Company;
import com.kmware.model.Country;
import com.kmware.model.Tax;
import com.kmware.web.bbean.CommonCRUDBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.openfaces.event.UploadCompletionEvent;
import org.openfaces.util.Faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ViewScoped
@ManagedBean(name = "taxCRUD")
public class TaxCrudBean extends CommonCRUDBean<Tax> {
	private static final long serialVersionUID = -9022037695214476125L;

    private List<Country> countries;

    public List<Country> getCountries(){
        if(countries == null){
            String query = "SELECT c FROM Country c WHERE c.deleted = false ORDER BY c.displayName ASC";
            countries = dao.getResultList(query, null, 0, 0, Country.class);
        }
        return countries;
    }

    public List<City> getCities(){
        String query = "SELECT c FROM City c WHERE c.deleted = false and c.country.id = :country ORDER BY c.displayName ASC";
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("country",entity.getCountry().getId());
        return dao.getResultList(query, params, 0, 0, City.class);
    }

    public List<Company> getCompanies(){
        String query = "SELECT DISTINCT(c) FROM Company c INNER JOIN c.cities cities WHERE c.deleted = false and :city IN cities ORDER BY c.displayName ASC";
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("city", entity.getCity());
        return dao.getResultList(query, params, 0, 0, Company.class);
    }

}
