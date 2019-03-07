package com.project.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    public LoginController() {
        super();
    }

    // API

    // custom login

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public void login(@RequestParam("username") final String username, @RequestParam("password") final String password, final HttpServletRequest request) {
//        UsernamePasswordAuthenticationToken authReq =
//            new UsernamePasswordAuthenticationToken(username, password);
//        Authentication auth = authManager.authenticate(authReq);
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
//    }
    
    @GetMapping(value="/login")
    public String signin(Model model, HttpServletRequest request) {
    
    	return "login";
    }

}