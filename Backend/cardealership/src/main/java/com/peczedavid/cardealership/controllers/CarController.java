package com.peczedavid.cardealership.controllers;

import java.util.List;

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
import com.peczedavid.cardealership.services.CarService;

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getCars(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "stock", required = false) Integer stock) {
        List<Car> cars = carService.getCars(brand, model, region, stock);
        return ResponseEntity.ok().body(cars);
    }


    @GetMapping(value="/{id}")
    public ResponseEntity<?> getCar(@NotNull @PathVariable Integer id) {
        Car car = carService.getById(id);
        if(car != null)
            return ResponseEntity.ok(car);
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteCar(@NotNull @PathVariable Integer id) {
        Car car = carService.deleteById(id);
        if(car != null)
            return ResponseEntity.ok(car);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveCar(@RequestBody CarRequest carRequest) {
        if(carRequest.getRegions() == null)
            return ResponseEntity.badRequest().body("Car should have at least 1 region.");
        
        Car car = carService.save(carRequest);
        return ResponseEntity.ok(car);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody CarRequest carRequest) {
        if(carRequest.getRegions() == null)
            return ResponseEntity.badRequest().body("Car should have at least 1 region.");
        
        Car car = carService.update(id, carRequest);
        return ResponseEntity.ok(car);
    }
}