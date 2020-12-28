
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

import com.jsf.entities.Person;

import jsf.learning_platform.dao.SectionDAO;
import jsf.learning_platform.dao.UserDAO;
import jsf.learning_platform.entities.Section;
import jsf.learning_platform.entities.User;

@Named
@ViewScoped
@FacesConfig
public class sectionBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_SECTION_LIST = "/pages/user/sectionList.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String name;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	@EJB
	SectionDAO sectionDAO;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Section> getFullList(){
		return sectionDAO.getFullList();
	}

	
	public List<Section> getList(){
		List<Section> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (name!= null && name.length() > 0){
			searchParams.put("name", name);
		}
		
		//2. Get list
		list = sectionDAO.getList(searchParams);
		
		return list;
	}

	public String newSection(){
		Section section = new Section();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("section", section);
		
		return PAGE_SECTION_LIST;
	}

	public String editSection(Section section){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("section", section);
		
		return PAGE_SECTION_LIST;
	}

	public String deleteSection(Section section){
		sectionDAO.remove(section);
		return PAGE_STAY_AT_THE_SAME;
	}
}
