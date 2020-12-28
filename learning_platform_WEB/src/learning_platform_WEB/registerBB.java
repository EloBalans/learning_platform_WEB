
package learning_platform_WEB;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.learning_platform.dao.UserDAO;
import jsf.learning_platform.entities.User;

@Named
@ViewScoped
@FacesConfig
public class registerBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_LOGIN = "/pages/public/login.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private User user = new User();
	
	public User getUser() {
		return user;
	}

	@Inject
	FacesContext context;

	@EJB
	UserDAO userDAO;


	@Inject
	Flash flash;

	public String saveData() {
		
		user.setRole("user");
		
		try {
			userDAO.create(user);

	} catch (Exception e) {
		e.printStackTrace();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", null));
		return PAGE_STAY_AT_THE_SAME;
	}

	return PAGE_LOGIN;
		
	}
}
