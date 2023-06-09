package com.example.SpringJdbcThymeCustomSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HaiLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String userName=request.getParameter("username");
        HaiUsers haiUsers=userService.findByUsername(userName);
        if(haiUsers!=null){
            if(haiUsers.getStatus()!=0){
                if(haiUsers.getAttempts()< haiUsers.getMaxAttempts()-1){
                    userService.increaseChances(haiUsers);
                    logger.info("Failure attempt");
                    exception = new LockedException("Failure attempts");
                }
                else {
                    userService.locking(haiUsers);
                    exception = new LockedException("Account locked due to 3 failure attempts");
                }
            }
        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
