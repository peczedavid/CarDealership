package com.peczedavid.cardealership.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.models.Car;
import com.peczedavid.cardealership.models.ERegion;
import com.peczedavid.cardealership.models.Region;
import com.peczedavid.cardealership.payload.request.CarRequest;
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

    @PostMapping("/new")
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
