package org.borave.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.secret}")
    private String secret;
    private static final long TOKEN_VALIDITY = 86400000; //1 dia

    public String createToken(Authentication authentication, String userId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_VALIDITY);
        List<String> topics = new ArrayList<>();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_VALIDITY);
        List<String> topics = new ArrayList<>();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public String resolveUsername(HttpServletRequest request) {
        String bearerToken = request.getHeader("user");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("User ")) {
            System.out.println("Entrou");
            return bearerToken.substring(5);
        }
        return null;
    }
public JwtTokenStatus validateToken(String token) {
    try {
        Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
        return JwtTokenStatus.VALID;
    } catch (MalformedJwtException ex) {
        return JwtTokenStatus.INVALID;
    } catch (ExpiredJwtException ex) {
        return JwtTokenStatus.EXPIRED;
    } catch (UnsupportedJwtException ex) {
        return JwtTokenStatus.UNSUPPORTED;
    } catch (IllegalArgumentException ex) {
        return JwtTokenStatus.EMPTY;
    } catch (SignatureException e) {
        return JwtTokenStatus.SIGNATURE;
    }
}

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isRealUser(String token, String username){
        if(username.equals(getUsername(token))){
            return true;
        }
        return false;
    }
}
