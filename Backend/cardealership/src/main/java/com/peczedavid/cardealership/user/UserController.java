package com.peczedavid.cardealership.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.region.Region;
import com.peczedavid.cardealership.region.RegionRepository;
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

    @PostMapping("/register")
    public ResponseEntity<?> regiser(@RequestBody RegisterRequest registerRequest) {
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

        return new ResponseEntity<String>("Account named: " + user.getUsername() + " successfully created.", HttpStatus.CREATED);
    }

}
