package com.project.web.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.project.api.data.enums.UserType;


public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	 
	private static final long serialVersionUID = 7936178364909160406L;
	private String verificationCode;
    private UserType userType;
 
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        verificationCode = request.getParameter("code");
       
        String userTypeStr = request.getParameter("user_type");
        if ( userTypeStr != null) {
        		userType = UserType.getById(Integer.valueOf(userTypeStr));
        }
    }
 
    public String getVerificationCode() {
        return verificationCode;
    }

	public UserType getUserType() {
		return userType;
	}
    
    
}