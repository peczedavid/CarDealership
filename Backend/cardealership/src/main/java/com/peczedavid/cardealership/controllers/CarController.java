package com.peczedavid.cardealership.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.models.Car;
import com.peczedavid.cardealership.payload.request.CarRequest;
import com.peczedavid.cardealership.security.jwt.JwtUtils;
import com.peczedavid.cardealership.services.CarService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> getCars(
            HttpServletRequest request,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "stock", required = false) Integer stock) {
        List<Car> cars = carService.getCars(brand, model, region, stock);
        return ResponseEntity.ok(cars);
        // Cookie[] cookies = request.getCookies();
        // if (cookies == null)
        // return ResponseEntity.badRequest().build(); // Needs to be logged in to
        // access cars

        // String userRegion = null;
        // for (Cookie cookie : cookies) {
        // // TODO: get the cookie name from the application.properties file
        // if (cookie.getName().equals("authCookie")) {
        // String jwtString = cookie.getValue();
        // userRegion = jwtUtils.getRegionFromToken(jwtString);
        // }
        // }
        // if (userRegion == null)
        // return ResponseEntity.badRequest().build(); // Needs to be logged in to
        // access cars

        // List<Car> filteredCars = new ArrayList<Car>();
        // for(Car car : cars) {
        // if(car.isInRegion(userRegion)) {
        // filteredCars.add(car);
        // }
        // }

        // return ResponseEntity.ok().body(filteredCars);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCar(@NotNull @PathVariable Integer id) {
        Car car = carService.getById(id);
        if (car != null)
            return ResponseEntity.ok(car);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCar(@NotNull @PathVariable Integer id) {
        Car car = carService.deleteById(id);
        if (car != null)
            return ResponseEntity.ok(car);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveCar(@RequestBody CarRequest carRequest) {
        if (carRequest.getRegions() == null)
            return ResponseEntity.badRequest().body("Car should have at least 1 region.");

        Car car = carService.save(carRequest);
        return ResponseEntity.ok(car);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody CarRequest carRequest) {
        if (carRequest.getRegions() == null)
            return ResponseEntity.badRequest().body("Car should have at least 1 region.");

        Car car = carService.update(id, carRequest);
        return ResponseEntity.ok(car);
    }
}