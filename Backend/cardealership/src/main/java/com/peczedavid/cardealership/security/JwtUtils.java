package com.peczedavid.cardealership.security;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
  @Value("${peczedavid.app.jwtSecret}")
  private String jwtSecret;
  @Value("${peczedavid.app.jwtExpirationMs}")
  private int jwtExpirationMs;
  @Value("${peczedavid.app.jwtCookieName}")
  private String jwtCookie;

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUser(userPrincipal);
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
        .path("/api").maxAge(24 * 60 * 60)
        .httpOnly(true) // because not showing up in chrome(otherwise=true)
        // .sameSite("None") // because not showing up in chrome
        // .secure(true) // because not showing up in chrome
        .build();
    return cookie;
  }

  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null)
        .path("/api")
        .maxAge(0)
        .build();
    return cookie;
  }

  public Long getIdFromJwtToken(String token) {
    Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    return Long.parseLong(claims.getBody().getSubject());
  }

  public String getUserNameFromJwtToken(String token) {
    Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    return claims.getBody().get("username", String.class);
  }

  public String getRegionFromToken(String token) {
    Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    return claims.getBody().get("region", String.class);
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  // TODO: store admin role in token(to claims?)
  public String generateTokenFromUser(UserDetailsImpl userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getId().toString())
        .claim("username", userDetails.getUsername())
        .claim("region", userDetails.getRegion())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }
}