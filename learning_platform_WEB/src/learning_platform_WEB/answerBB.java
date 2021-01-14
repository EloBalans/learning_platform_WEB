
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

import jsf.learning_platform.dao.AnswerDAO;
import jsf.learning_platform.entities.Answer;
import jsf.learning_platform.entities.Math;

@Named
@ViewScoped
@FacesConfig
public class answerBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_SECTION_LIST = "/pages/user/sectionList.xhtml?faces-redirect=true";
	private static final String PAGE_ADDMATH = "/pages/admin/addMath.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Answer answer = new Answer();
	private Math math = new Math();

	@Inject
	FacesContext context;

	@EJB
	AnswerDAO answerDAO;
	
	public Answer getAnswer() {
		return answer;
	}
	
	public String createAnswer() {
		
		answer.setGrade("1");
		answer.setPoints(0);
		answer.setMath(math);

		try {
				answerDAO.create(answer);
			
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_STAY_AT_THE_SAME;
	}
}
