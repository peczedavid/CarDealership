package com.peczedavid.cardealership.car;

import java.util.List;

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

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/multiple")
    public ResponseEntity<?> createCars(@RequestBody List<CarRequest> carRequests) {
        List<Car> cars = carService.create(carRequests);
        return new ResponseEntity<List<Car>>(cars, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> createCar(@RequestBody CarRequest carRequest) {
        Car car = carService.create(carRequest.getBrand(), carRequest.getModel(), carRequest.getRegion(), carRequest.getStock());
        if(car == null) new ResponseEntity<String>("Faield to create car", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Car>(car, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getCars(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "stock", required = false) Integer stock) {
        List<Car> cars = carService.find(brand, model, region, stock);
        return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable Integer id) {
        Car car = carService.findById(id);
        if(car == null) return new ResponseEntity<String>("Car not found with given id!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Integer id, @RequestBody CarRequest carRequest) {
        Car car = carService.update(id, carRequest);
        if(car == null) return new ResponseEntity<String>("Error: Car not found with id: " + id + "!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<Car>(car, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id) {
        if(carService.deletById(id)) return new ResponseEntity<String>("Deleted car with id: " + id + ".", HttpStatus.NO_CONTENT);
        else return new ResponseEntity<String>("Error: Car not found with id: " + id + "!", HttpStatus.NOT_FOUND);
    }
}
