package com.kmware.web.bbean.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.openfaces.event.UploadCompletionEvent;

import com.kmware.model.Country;
import com.kmware.web.bbean.CommonCRUDBean;

@ViewScoped
@ManagedBean(name = "countryCRUD")
public class CountryCrudBean extends CommonCRUDBean<Country> {
	private static final long serialVersionUID = -9022037695214476125L;

	public void uploadComplete(UploadCompletionEvent e) {
		if(e.getFiles()!=null && e.getFiles().size() == 1){
			File f = e.getFiles().get(0).getFile();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			  try {
				ImageIO.write(ImageIO.read(f), "png", baos);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			  
			 String encoded = Base64.encodeBase64String(baos.toByteArray());
			 System.out.println("encoded: "+encoded);
		}
	}

}
