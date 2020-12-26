package learning_platform_WEB;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import jsf.learning_platform.dao.UserDAO;
import jsf.learning_platform.entities.User;

@Named
@RequestScoped
public class loginBB {

	private static final String PAGE_MAIN = "/pages/user/sectionList?faces-redirect=true";
	private static final String PAGE_LOGIN = "/pages/public/login?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Inject
	UserDAO userDAO;

	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		List<User> users = userDAO.getUser(email, password);

		if (users.isEmpty()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Wrong login or password", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		User user = users.get(0);
		
		RemoteClient<User> client = new RemoteClient<User>(); 
		client.setDetails(user);
		
		client.getRoles().add(user.getRole());
	
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);

		return PAGE_MAIN;
	}
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		session.invalidate();
		return PAGE_LOGIN;
	}
}
