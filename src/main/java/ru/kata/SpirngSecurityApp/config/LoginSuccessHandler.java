package ru.kata.SpirngSecurityApp.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kata.SpirngSecurityApp.security.UserDetailsImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String redirectURL = request.getContextPath();

        if (userDetails.hasRole("ROLE_ADMIN")) {
            redirectURL = "/admin/admin";
        } else {
            redirectURL = "/user";
        }

        response.sendRedirect(redirectURL);
    }
}
