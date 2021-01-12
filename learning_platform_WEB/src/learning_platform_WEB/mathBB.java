
package learning_platform_WEB;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.learning_platform.dao.MathDAO;
import jsf.learning_platform.entities.Math;


@Named
@ViewScoped
@FacesConfig
public class mathBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_SECTION_LIST = "/pages/user/sectionList.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String math_id;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	@EJB
	MathDAO mathDAO;
	
	public String getMath_id() {
		return math_id;
	}
	
	public void setMath_id(String math_id) {
		this.math_id= math_id;
	}
	
	public List<Math> getFullList(){
		return mathDAO.getFullList();
	}

	
	public List<Math> getList(){
		List<Math> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (math_id!= null && math_id.length() > 0){
			searchParams.put("math_id", math_id);
		}
		
		list = mathDAO.getList(searchParams);
		
		return list;
	}

	public String newMath(){
		Math math = new Math();
		
		flash.put("math", math);
		
		return PAGE_SECTION_LIST;
	}

	public String editMath(Math math){
		
		flash.put("math", math);
		
		return PAGE_SECTION_LIST;
	}

	public String deleteMath(Math math){
		mathDAO.remove(math);
		return PAGE_STAY_AT_THE_SAME;
	}
}
