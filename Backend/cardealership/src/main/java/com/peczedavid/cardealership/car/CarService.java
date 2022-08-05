package com.peczedavid.cardealership.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peczedavid.cardealership.region.Region;

@Service
public class CarService {
    
    @Autowired
    private CarRepository carRepository;

    public void deleteAll() {
        carRepository.deleteAll();
    }

    public Car create(String brand, String model, Region region) {
        Car car = new Car(brand, model, region);
        try {
            car = carRepository.save(car);
            return car;
        } catch (IllegalArgumentException e) {
            System.out.println("Car already exists with that name!");
            return null;
        }
    }
}
