package org.borave.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.borave.service.ProfileService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;

    public JwtAuthenticationEntryPoint(JwtTokenProvider jwtTokenProvider, ProfileService profileService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.profileService = profileService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        JwtTokenStatus tokenStatus = JwtTokenStatus.INVALID;

        if (authException instanceof JwtAuthenticationException) {
            tokenStatus = ((JwtAuthenticationException) authException).getTokenStatus();
        }
        String userId = jwtTokenProvider.resolveUsername(request);
        String username = profileService.getUsernameById(userId);
        if(tokenStatus.equals(JwtTokenStatus.EXPIRED) && username != null) {
            String token = jwtTokenProvider.resolveToken(request);
            System.out.println(token);
            String newToken = jwtTokenProvider.createToken(username);
            response.getWriter().write("{ \"message\": \"" + getErrorMessage(tokenStatus) + "\", \"newToken\": \"" + newToken + "\" }");
        }else{
            response.getWriter().write("{ \"message\": \"" + getErrorMessage(tokenStatus) + "\" }");
        }
    }

    private String getErrorMessage(JwtTokenStatus tokenStatus) {
        switch (tokenStatus) {
            case INVALID:
                return "Invalid JWT token";
            case EXPIRED:
                return "Expired JWT token";
            case UNSUPPORTED:
                return "Unsupported JWT token";
            case EMPTY:
                return "JWT claims string is empty";
            case SIGNATURE:
                return "Error with the signature of your token";
            default:
                return "Authentication failed";
        }
    }
    @Override
    public void afterPropertiesSet() {
        setRealmName("JWT Authentication");
        super.afterPropertiesSet();
    }
}