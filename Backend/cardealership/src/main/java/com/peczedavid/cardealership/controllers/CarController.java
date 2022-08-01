package com.peczedavid.cardealership.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.models.Car;
import com.peczedavid.cardealership.services.CarService;

@CrossOrigin(origins = {"http://localhost:8081"}, maxAge = 3600, allowCredentials = "true")
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
        List<Car> cars = carService.getCars();

        if (brand != null) {
            cars = cars.stream().filter(car -> car.getBrand().contains(brand)).collect(Collectors.toList());
        }
        if (model != null) {
            cars = cars.stream().filter(car -> car.getModel().contains(model)).collect(Collectors.toList());
        }
        if (region != null) {
            cars = cars.stream().filter(car -> car.isInRegion(region)).collect(Collectors.toList());
        }
        if(stock != null) {
            cars = cars.stream().filter(car -> car.getStock().equals(stock)).collect(Collectors.toList());
        }

        return ResponseEntity.ok().body(cars);
    }

}

// @CrossOrigin(origins = "*", maxAge = 3600)
// @RestController
// @RequestMapping("/api/cars")
// public class CarController {

// @Autowired
// private CarRepository carRepository;

// @Autowired
// private CarService carService;

// @Autowired
// private RegionRepository regionRepository;

// @DeleteMapping("/deleteById/{id}")
// @PreAuthorize("hasRole('ADMIN')")
// public ResponseEntity<?> deleteCarById(@PathVariable int id) {
// Car car = carService.deleteById((long) id);
// if (car != null) {
// return ResponseEntity.ok().body(car);
// }
// return ResponseEntity.notFound().build();
// }

// @GetMapping()
// public ResponseEntity<?> getCars(
// @RequestParam(name = "brand", required = false) String brand,
// @RequestParam(name = "model", required = false) String model,
// @RequestParam(name = "region", required = false) String region) {

// List<Car> cars = carService.getCars();
// if (brand != null) {
// cars = cars.stream().filter(car ->
// car.getBrand().contains(brand)).collect(Collectors.toList());
// }
// if (model != null) {
// cars = cars.stream().filter(car ->
// car.getModel().contains(model)).collect(Collectors.toList());
// }
// if (region != null) {
// // TODO:
// }

// return ResponseEntity.ok().body(cars);
// }

// @GetMapping("/getByBrand/{brand}")
// public ResponseEntity<?> getCarsByBrand(@PathVariable String brand) {
// List<Car> cars = carService.getCarsByBrand(brand);
// if (cars.size() > 0)
// return ResponseEntity.ok().body(cars);
// return ResponseEntity.notFound().build();
// }

// @GetMapping("/getById/{id}")
// public ResponseEntity<?> getCarById(@PathVariable int id) {
// Car car = carService.getCarById(id);
// if (car != null) {
// return ResponseEntity.ok(car);
// }
// return ResponseEntity.notFound().build();
// }

// @PutMapping("/updateById/{id}")
// @PreAuthorize("hasRole('ADMIN')")
// public ResponseEntity<?> updateCar(@RequestBody CarRequest carRequest,
// @PathVariable int id) {
// Car car = carRepository.findById((long) id).orElse(null);
// if (car != null) {
// car.setBrand(carRequest.getBrand());
// car.setModel(carRequest.getModel());
// Set<String> strRegions = carRequest.getRegion();
// Set<Region> regions = new HashSet<>();
// if (strRegions == null) {

// } else {
// strRegions.forEach(region -> {
// switch (region) {
// case "germany":
// Region germanRegion = regionRepository.findByName(ERegion.Germany)
// .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
// regions.add(germanRegion);
// break;
// case "japan":
// Region japanRegion = regionRepository.findByName(ERegion.Japan)
// .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
// regions.add(japanRegion);
// break;
// case "america":
// Region americaRegion = regionRepository.findByName(ERegion.America)
// .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
// regions.add(americaRegion);
// break;
// default:
// break;
// }
// });
// }
// car.setRegions(regions);
// carRepository.save(car);
// return ResponseEntity.ok().body(
// new CarResponse(car.getId(),
// car.getBrand(),
// car.getModel(),
// strRegions,
// carRequest.getStock()));
// }
// return ResponseEntity.badRequest()
// .body(new MessageResponse(
// new StringBuilder()
// .append("Car not found with id: ")
// .append(id)
// .toString()));
// }

// @PostMapping("/new")
// @PreAuthorize("hasRole('ROLE_ADMIN')")
// public String newCar(@RequestBody CarRequest carRequest) {
// Car car = new Car(carRequest.getBrand(), carRequest.getModel());
// Set<String> strRegions = carRequest.getRegion();
// Set<Region> regions = new HashSet<>();
// if (strRegions == null) {

// } else {
// strRegions.forEach(region -> {
// switch (region) {
// case "germany":
// Region germanRegion = regionRepository.findByName(ERegion.Germany)
// .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
// regions.add(germanRegion);
// break;
// case "japan":
// Region japanRegion = regionRepository.findByName(ERegion.Japan)
// .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
// regions.add(japanRegion);
// break;
// case "america":
// Region americaRegion = regionRepository.findByName(ERegion.America)
// .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
// regions.add(americaRegion);
// break;
// default:
// break;
// }
// });
// }
// car.setRegions(regions);
// carRepository.save(car);
// return car.getBrand() + " " + car.getModel();
// }
// }
