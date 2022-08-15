package com.peczedavid.cardealership.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.region.Region;
import com.peczedavid.cardealership.region.RegionRepository;
import com.peczedavid.cardealership.security.JwtUtils;
import com.peczedavid.cardealership.security.UserDetailsImpl;
import com.peczedavid.cardealership.user.payload.LoginRequest;
import com.peczedavid.cardealership.user.payload.UserData;
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

    @GetMapping("paging/{offset}/{pageSize}/{field}")
    private ResponseEntity<Page<UserData>> getUserPagingAndSorting(@PathVariable int offset, @PathVariable int pageSize,
            @PathVariable String field) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));

        Page<UserData> userDataPage = userPage.map(user -> new UserData(user));
        return new ResponseEntity<Page<UserData>>(userDataPage, HttpStatus.OK);
    }

    @GetMapping("/paging/{field}")
    private ResponseEntity<List<UserData>> getUsersPagingTest(@PathVariable String field) {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, field));

        List<UserData> userDatas = users.stream().map(user -> new UserData(user)).collect(Collectors.toList());
        return new ResponseEntity<List<UserData>>(userDatas, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserData>> getAllUsers(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        if (jwt == null || !jwtUtils.getAdminFromToken(jwt)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<UserData> users = userRepository
                .findAll()
                .stream()
                .map(user -> new UserData(user))
                .collect(Collectors.toList());

        return new ResponseEntity<List<UserData>>(users, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        if (jwt == null) {
            // No user logged in
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Long id = jwtUtils.getIdFromJwtToken(jwt);
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return new ResponseEntity<String>("Error: User not found with id: " + id + " !", HttpStatus.NOT_FOUND);
        UserData userData = new UserData(user);

        return new ResponseEntity<UserData>(userData, HttpStatus.OK);
    }

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

        UserData userData = UserData
                .builder()
                .id(userPrincipal.getId())
                .username(userPrincipal.getUsername())
                .admin(userPrincipal.isAdmin())
                .region(region)
                .build();

        return new ResponseEntity<UserData>(userData, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> regiser(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
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

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(), registerRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Cookie cookie = jwtUtils.generateJwtCookie(userPrincipal);
        response.addCookie(cookie);

        UserData loginResponse = UserData
                .builder()
                .id(userPrincipal.getId())
                .username(userPrincipal.getUsername())
                .admin(userPrincipal.isAdmin())
                .region(region)
                .build();

        return new ResponseEntity<UserData>(loginResponse, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = jwtUtils.getCleanJwtCookie();
        response.addCookie(cookie);

        return new ResponseEntity<String>("Successfully logged out.", HttpStatus.OK);
    }
}
