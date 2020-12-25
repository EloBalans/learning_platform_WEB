package learning_platform_WEB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;

import jsf.learning_platform.dao.UserDAO;
import jsf.learning_platform.entities.User;

public class userDataBB {
	private static final String PAGE_CASTLE_EDIT = "castleEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String username;
	
	ExternalContext extcontext;
	
	Flash flash;
	
	
	@EJB
	UserDAO userDAO;
		
	public String getusername() {
		return username;
	}

	public void setname(String username) {
		this.username = username;
	}


	public List<User> getList(){
		List<User> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (username != null && username.length() > 0){
			searchParams.put("username", username);
		}
		
		//2. Get list
		list = userDAO.getListVer(searchParams);
		
		return list;
	}


	public String editUser(User user){
		
		//2. Pass object through flash 
		flash.put("user", userr);
		
		return PAGE_CASTLE_EDIT;
	}

	
}
