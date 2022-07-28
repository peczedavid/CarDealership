package com.peczedavid.cardealership.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.models.Car;
import com.peczedavid.cardealership.models.ERegion;
import com.peczedavid.cardealership.models.Region;
import com.peczedavid.cardealership.payload.request.CarRequest;
import com.peczedavid.cardealership.payload.response.CarResponse;
import com.peczedavid.cardealership.payload.response.MessageResponse;
import com.peczedavid.cardealership.repositories.CarRepository;
import com.peczedavid.cardealership.repositories.RegionRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping("/")
    public String allAccess() {
        return "cars";
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCarById(@PathVariable int id) {
        Car car = carRepository.findById((long)id).orElse(null);
        if(car != null) {
            carRepository.delete(car);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getCarByBrand(@PathVariable int id) {
        Car car = carRepository.findById((long)id).orElse(null);
        if(car != null) {
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCar(@RequestBody CarRequest carRequest, @PathVariable int id) {
        Car car = carRepository.findById((long) id).orElse(null);
        if (car != null) {
            car.setBrand(carRequest.getBrand());
            car.setModel(carRequest.getModel());
            Set<String> strRegions = carRequest.getRegion();
            Set<Region> regions = new HashSet<>();
            if (strRegions == null) {

            } else {
                strRegions.forEach(region -> {
                    switch (region) {
                        case "germany":
                            Region germanRegion = regionRepository.findByName(ERegion.Germany)
                                    .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
                            regions.add(germanRegion);
                            break;
                        case "japan":
                            Region japanRegion = regionRepository.findByName(ERegion.Japan)
                                    .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
                            regions.add(japanRegion);
                            break;
                        case "america":
                            Region americaRegion = regionRepository.findByName(ERegion.America)
                                    .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
                            regions.add(americaRegion);
                            break;
                        default:
                            break;
                    }
                });
            }
            car.setRegions(regions);
            carRepository.save(car);
            return ResponseEntity.ok().body(
                new CarResponse(car.getId(), car.getBrand(), car.getModel(), strRegions)
            );
        }
        return ResponseEntity.badRequest()
        .body(
            new MessageResponse(
                new StringBuilder()
                .append("Car not found with id: ")
                .append(id)
                .toString()));
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String newCar(@RequestBody CarRequest carRequest) {
        Car car = new Car(carRequest.getBrand(), carRequest.getModel());
        Set<String> strRegions = carRequest.getRegion();
        Set<Region> regions = new HashSet<>();
        if (strRegions == null) {

        } else {
            strRegions.forEach(region -> {
                switch (region) {
                    case "germany":
                        Region germanRegion = regionRepository.findByName(ERegion.Germany)
                                .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
                        regions.add(germanRegion);
                        break;
                    case "japan":
                        Region japanRegion = regionRepository.findByName(ERegion.Japan)
                                .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
                        regions.add(japanRegion);
                        break;
                    case "america":
                        Region americaRegion = regionRepository.findByName(ERegion.America)
                                .orElseThrow(() -> new RuntimeException("Error: Region is not found."));
                        regions.add(americaRegion);
                        break;
                    default:
                        break;
                }
            });
        }
        car.setRegions(regions);
        carRepository.save(car);
        return car.getBrand() + " " + car.getModel();
    }
}
