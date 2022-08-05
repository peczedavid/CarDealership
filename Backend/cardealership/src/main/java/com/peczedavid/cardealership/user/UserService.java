package com.peczedavid.cardealership.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peczedavid.cardealership.region.Region;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    // TODO: a lot more checking and password encryption
    public User create(String username, String password, boolean admin, Region region) {
        User user = new User(username, password, admin, region);
        try {
            user = userRepository.save(user);
            return user;
        } catch (IllegalArgumentException e) {
            System.out.println("User already exists with that name!");
            return null;
        }
    }
}
