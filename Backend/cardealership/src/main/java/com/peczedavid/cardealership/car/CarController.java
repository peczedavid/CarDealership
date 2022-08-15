package com.peczedavid.cardealership.car;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.springframework.http.HttpStatus;
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

import com.peczedavid.cardealership.car.payload.CarRequest;
import com.peczedavid.cardealership.security.JwtUtils;

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private JwtUtils jwtUtils;

    private boolean checkUnauthorized(HttpServletRequest request, Car car) {
        String jwtCookie = jwtUtils.getJwtFromCookies(request);
        Boolean admin = jwtUtils.getAdminFromToken(jwtCookie);
        String region = jwtUtils.getRegionFromToken(jwtCookie);
        return (!car.getRegion().getName().equals(region) && !admin);
    }

    private boolean checkUnauthorized(HttpServletRequest request, CarRequest carRequest) {
        String jwtCookie = jwtUtils.getJwtFromCookies(request);
        Boolean admin = jwtUtils.getAdminFromToken(jwtCookie);
        String region = jwtUtils.getRegionFromToken(jwtCookie);
        return (!carRequest.getRegion().equals(region) && !admin);
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createCars(@RequestBody List<CarRequest> carRequests) {
        List<Car> cars = carService.create(carRequests);
        return new ResponseEntity<List<Car>>(cars, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> createCar(HttpServletRequest request, @RequestBody CarRequest carRequest) {
        // Authorization
        if (checkUnauthorized(request, carRequest))
            return new ResponseEntity<String>(
                    "Error: User is not admin and can only do operations on cars from their region!",
                    HttpStatus.UNAUTHORIZED);

        Car car = carService.create(carRequest.getBrand(), carRequest.getModel(), carRequest.getRegion(),
                carRequest.getStock(), carRequest.getDescription());
        if (car == null)
            new ResponseEntity<String>("Failed to create car", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Car>(car, HttpStatus.CREATED);
    }

    @GetMapping("/filtered")
    public ResponseEntity<?> getCarsFilter(
            HttpServletRequest request,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "stock", required = false) Integer stock,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "sort", required = false) String sort) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        boolean admin = jwtUtils.getAdminFromToken(jwt);
        String userRegion = jwtUtils.getRegionFromToken(jwt);

        // If the user is not admin, they can only view cars from their region
        List<Car> cars = carService.findByFilters(brand, model, admin ? region : userRegion, stock,
                description, sort == null ? "" : sort);
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getCarsQuery(
            HttpServletRequest request,
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "sort", required = false) String sort) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        boolean admin = jwtUtils.getAdminFromToken(jwt);
        String userRegion = jwtUtils.getRegionFromToken(jwt);

        List<Car> cars = carService.findByQuery(query, sort == null ? "" : sort);
        // If the user is not admin, they can only view cars from their region
        if (!admin)
            cars = cars.stream().filter(car -> car.getRegion().getName().toUpperCase().equals(userRegion.toUpperCase()))
                    .collect(Collectors.toList());
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(HttpServletRequest request, @PathVariable Integer id) {
        Car car = carService.findById(id);
        if (car == null)
            return new ResponseEntity<String>("Car not found with given id!", HttpStatus.NOT_FOUND);

        // Authorization
        if (checkUnauthorized(request, car))
            return new ResponseEntity<String>(
                    "Error: User is not admin and can only do operations on cars from their region!",
                    HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(HttpServletRequest request, @PathVariable Integer id,
            @RequestBody CarRequest carRequest) {
        Car car = carService.update(id, carRequest);
        if (car == null)
            return new ResponseEntity<String>("Error: Car not found with id: " + id + "!", HttpStatus.NOT_FOUND);

        // Authorization
        if (checkUnauthorized(request, car))
            return new ResponseEntity<String>(
                    "Error: User is not admin and can only do operations on cars from their region!",
                    HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(HttpServletRequest request, @PathVariable Integer id) {
        Car car = carService.findById(id);
        if (car == null)
            return new ResponseEntity<String>("Error: Car not found with id: " + id + "!", HttpStatus.NOT_FOUND);

        // Authorization
        if (checkUnauthorized(request, car))
            return new ResponseEntity<String>(
                    "Error: User is not admin and can only do operations on cars from their region!",
                    HttpStatus.UNAUTHORIZED);

        if (!carService.deletById(id))
            return new ResponseEntity<String>("Error: Couldn't delete car with id: " + id + "!", HttpStatus.NO_CONTENT);

        return new ResponseEntity<String>("Deleted car with id: " + id + ".", HttpStatus.NO_CONTENT);
    }
}
