
package learning_platform_WEB;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.learning_platform.dao.MathDAO;
import jsf.learning_platform.dao.SectionDAO;
import jsf.learning_platform.dao.AnswerDAO;
import jsf.learning_platform.entities.Math;
import jsf.learning_platform.entities.Section;
import jsf.learning_platform.entities.Answer;


@Named
@ViewScoped
@FacesConfig
public class mathQuizBB implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mathId;
	
	private static final String PAGE_STAY = null;
	private static final String PAGE_QUIZ = "/pages/user/quiz.xhtml?faces-redirect=true";
	

	private Section section = new Section();
	private Math math = new Math();
	private Math loaded = null;
	
	
	
	private String clientAnswer;
	
	private Math answerRight = new Math();
	private String answer;
	
	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	@EJB
	MathDAO mathDAO;
	@EJB
	SectionDAO sectionDAO;
	
	
	public Section getSection() {
		return section;
	}
	
	public Math getAnswerRight() {
		return answerRight;
	}
	
	public Math getMath() {
		return math;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getClientAnswer() {
		return clientAnswer;
	}
	
	public void setClientAnswer(String clientAnswer) {
		this.clientAnswer = clientAnswer;
	}
	
	public Math Quiz(){
		math = mathDAO.find(math.getMathId());
		return math;
		
	}
	
	
	
	
	
	public void checkAnswer() {
		answerRight = mathDAO.find(math.getMathId());
		
		if(answerRight.getCorrectAnswer().equals(answer)) {
			 clientAnswer = "Poprawna odpowiedz";
		}else
			
			clientAnswer = "Z�a odpowiedz";
	}
	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && math.getMathId() != null) {
				loaded = mathDAO.find(math.getMathId());
			}
			if (loaded != null) {
				math = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
				// if (!context.isPostback()) { // possible redirect
				// context.getExternalContext().redirect("personList.xhtml");
				// context.responseComplete();
				// }
			}
		}

	}
	
	

}
