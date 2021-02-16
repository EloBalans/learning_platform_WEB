
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
import jsf.learning_platform.dao.SectionDAO;
import jsf.learning_platform.dao.AnswerDAO;
import jsf.learning_platform.entities.Math;
import jsf.learning_platform.entities.Section;
import jsf.learning_platform.entities.Answer;


@Named
@ViewScoped
@FacesConfig
public class mathBB implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mathId;

	private static final String PAGE_SECTION_LIST = "/pages/user/sectionList.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = "/pages/admin/addMath.xhtml?faces-redirect=true";
	private static final String PAGE_STAY = null;
	private static final String PAGE_QUIZ = "/pages/user/quiz.xhtml?faces-redirect=true";
	

	private Section section = new Section();
	private Math math = new Math();
	private Math loaded = null;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	@EJB
	MathDAO mathDAO;
	@EJB
	SectionDAO sectionDAO;
	
	public String getMathId() {
		return mathId;
	}
	
	public void setMathId(String mathId) {
		this.mathId = mathId;
	}
	
	public Section getSection() {
		return section;
	}
	
	public Math getMath() {
		return math;
	}
	
	
	
	public List<Math> getFullList(){
		return mathDAO.getFullList();
	}
	
	public List<Math> getQuiz(){
		List<Math> list = null;
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (mathId != null && mathId.length() > 0){
			searchParams.put("mathId", mathId);
		}
		
		//2. Get list
		list = mathDAO.getList(searchParams);
		
		return list;
		
	}
	
	public String toSection() {
		return PAGE_SECTION_LIST;
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
	
	public String saveData() {
		
		section = sectionDAO.find(section.getSectionId());
		math.setSection(section);
		
		try {
			
			mathDAO.create(math);

		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String deleteMath(Math math){
		mathDAO.remove(math);
		return PAGE_STAY;
	}
	
	public String showOneQuiz() {
		math = math.get;
		mathDAO.getList(math);
		return PAGE_QUIZ;
	}
	

}
