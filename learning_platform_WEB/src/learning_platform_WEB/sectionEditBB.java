
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
public class sectionEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_SECTION_LIST = "/pages/user/sectionList.xhtml?faces-redirect=true";
	private static final String PAGE_ADDMATH = "/pages/admin/addMath.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Section section = new Section();
	private Section loaded = null;

	@Inject
	FacesContext context;

	@EJB
	SectionDAO sectionDAO;
	
	public Section getSection() {
		return section;
	}
	
	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && section.getSectionId() != null) {
				loaded = sectionDAO.find(section.getSectionId());
			}
			if (loaded != null) {
				section = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "BÅ‚Ä™dne uÅ¼ycie systemu", null));
				// if (!context.isPostback()) { // possible redirect
				// context.getExternalContext().redirect("personList.xhtml");
				// context.responseComplete();
				// }
			}
		}

	}
	
	public String saveData() {
		

		try {
				sectionDAO.create(section);
			
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_SECTION_LIST;
	}
}
