package com.peczedavid.cardealership.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.region.Region;
import com.peczedavid.cardealership.region.RegionRepository;
import com.peczedavid.cardealership.security.JwtUtils;
import com.peczedavid.cardealership.security.UserDetailsImpl;
import com.peczedavid.cardealership.user.payload.LoginRequest;
import com.peczedavid.cardealership.user.payload.LoginResponse;
import com.peczedavid.cardealership.user.payload.RegisterRequest;

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Cookie cookie = jwtUtils.generateJwtCookie(userPrincipal);
        response.addCookie(cookie);

        Region region = regionRepository.findByName(userPrincipal.getRegion()).orElse(null);

        LoginResponse loginResponse = LoginResponse
                .builder()
                .id(userPrincipal.getId())
                .username(userPrincipal.getUsername())
                .admin(userPrincipal.isAdmin())
                .region(region)
                .build();

        return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> regiser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername()))
            return new ResponseEntity<String>("Error: Username is already taken.", HttpStatus.BAD_REQUEST);

        Region region = regionRepository
                .findByName(registerRequest.getRegion())
                .orElseThrow(
                        () -> new RuntimeException("Error: Region " + registerRequest.getRegion() + " not found!"));

        User user = User
                .builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .admin(registerRequest.isAdmin())
                .region(region)
                .build();

        userRepository.save(user);

        return new ResponseEntity<String>("Account named: " + user.getUsername() + " successfully created.",
                HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = jwtUtils.getCleanJwtCookie();
        response.addCookie(cookie);

        return new ResponseEntity<String>("Successfully logged out.", HttpStatus.OK);
    }
}
