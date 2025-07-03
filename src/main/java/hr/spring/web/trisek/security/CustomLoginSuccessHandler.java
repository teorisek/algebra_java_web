package hr.spring.web.trisek.security;

import hr.spring.web.trisek.service.LoginAuditService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginAuditService loginAuditService;

    public CustomLoginSuccessHandler(LoginAuditService loginAuditService) {
        this.loginAuditService = loginAuditService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String ip = request.getRemoteAddr();
        loginAuditService.logLogin(username, ip);

        response.sendRedirect("/items");
    }
}