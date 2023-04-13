package com.example.SpringJdbcThymeCustomSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HaiLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    @Autowired
    UserService service;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HaiUsers haiUsers = (HaiUsers) authentication.getPrincipal();
        if(haiUsers.getStatus()!=0){
            if(haiUsers.getAttempts()>0){
                haiUsers.setAttempts(0);
                service.updateAttempt(haiUsers);
            }
            super.setDefaultTargetUrl("/web/dash");
            super.onAuthenticationSuccess(request, response, authentication);
        }
        else {
            logger.error("Maximum attempt reached contact admin to reset");
            super.setDefaultTargetUrl("/web/login");
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
