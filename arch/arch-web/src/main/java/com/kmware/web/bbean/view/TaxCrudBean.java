package com.kmware.web.bbean.view;

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
import java.util.List;

@ViewScoped
@ManagedBean(name = "taxCRUD")
public class TaxCrudBean extends CommonCRUDBean<Tax> {
	private static final long serialVersionUID = -9022037695214476125L;

    public List<Country> countryAutocomplete(){
        String searchString = Faces.var("searchString", String.class);
        List<Country> result = new ArrayList<Country>(0);
        if(StringUtils.isNotBlank(searchString)){
              result = dao.getEntitiesByDisplayName("%"+searchString.toLowerCase()+"%",0,15,Country.class);
        }
        return result;
    }

}
