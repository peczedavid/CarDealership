package com.peczedavid.cardealership.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.models.ERegion;
import com.peczedavid.cardealership.models.ERole;
import com.peczedavid.cardealership.models.Region;
import com.peczedavid.cardealership.models.Role;
import com.peczedavid.cardealership.models.User;
import com.peczedavid.cardealership.payload.request.LoginRequest;
import com.peczedavid.cardealership.payload.request.SignupRequest;
import com.peczedavid.cardealership.payload.response.MessageResponse;
import com.peczedavid.cardealership.payload.response.UserInfoResponse;
import com.peczedavid.cardealership.repositories.RegionRepository;
import com.peczedavid.cardealership.repositories.RoleRepository;
import com.peczedavid.cardealership.repositories.UserRepository;
import com.peczedavid.cardealership.security.jwt.JwtUtils;
import com.peczedavid.cardealership.security.services.UserDetailsImpl;

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	RegionRepository regionRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(new UserInfoResponse(userDetails.getId(),
						userDetails.getUsername(),
						userDetails.getEmail(),
						roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRegions = signUpRequest.getRegions();
		Set<Region> regions = new HashSet<>();
		if (strRegions == null) {

		} else {
			strRegions.forEach(regionStr -> {
				Region region = regionRepository.findByName(ERegion.fromString(regionStr))
						.orElseThrow(() -> new RuntimeException("Error: Region is not found."));
				regions.add(region);
			});
		}
		user.setRegions(regions);

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}

	public Long getIdFromServletRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (Cookie cookie : cookies) {
			// TODO: get the cookie name from the application.properties file
			if (cookie.getName().equals("authCookie")) {
				String jwtString = cookie.getValue();
				return jwtUtils.getIdFromJwtToken(jwtString);
			}
		}
		return null;
	}

	@GetMapping()
	public ResponseEntity<?> getUser(HttpServletRequest request) {
		Long id = getIdFromServletRequest(request);
		if (id == null)
			return ResponseEntity.badRequest().build();
		User user = userRepository.findById(id).orElse(null);
		List<String> roles = new ArrayList<Role>(user.getRoles()).stream()
				.map(item -> item.getName().toString())
				.collect(Collectors.toList());
		List<String> regions = new ArrayList<Region>(user.getRegions()).stream()
				.map(item -> item.getName().toString())
				.collect(Collectors.toList());
		return ResponseEntity
				.ok(new UserInfoResponse(user.getId(), user.getUsername(), user.getEmail(), roles, regions));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		User user = userRepository.findById((long) id).orElse(null);
		if (user != null) {
			userRepository.delete(user);
			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
					.body("User deleted!");
		}
		return ResponseEntity.notFound().build();
	}
}