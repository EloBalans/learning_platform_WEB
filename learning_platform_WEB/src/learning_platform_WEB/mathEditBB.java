
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
public class mathEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_SECTION_LIST = "/pages/user/sectionList.xhtml?faces-redirect=true";
	private static final String PAGE_ADDMATH = "/pages/admin/addMath.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Math math = new Math();
	private Math loaded = null;
	
	@Inject
	FacesContext context;

	@EJB
	MathDAO mathDAO;
	
	public Math getMath() {
		return math;
	}
	
	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && math.getMathId() != null) {
				loaded = mathDAO.find(math.getMathId());
			}
			if (loaded != null) {
				math = loaded;
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
				mathDAO.create(math);
			
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_ADDMATH;
	}
}
