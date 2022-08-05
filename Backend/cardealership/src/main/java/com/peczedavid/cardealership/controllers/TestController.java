package com.peczedavid.cardealership.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.security.jwt.JwtUtils;

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/public")
    public String getPublicContent() {
        return "Public content";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAdminContent() {
        return "Admin content";
    }

    @GetMapping("/cookie")
    public String cookieTest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // TODO: get the cookie name from the application.properties file
                if (cookie.getName().equals("authCookie")) {
                    String jwtString = cookie.getValue();
                    String region = jwtUtils.getRegionFromToken(jwtString);
                    if (region.equals("America")) {
                        return "Here you go, american cookies.";
                    }
                }
            }
        }
        return "Only americans get cookies!";
    }

    @GetMapping("/america")
    @PreAuthorize("hasAuthority('America')")
    public String getAmericanContent() {
        return "American content";
    }
}
