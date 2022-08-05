package com.peczedavid.cardealership.car;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peczedavid.cardealership.region.Region;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public void deleteAll() {
        carRepository.deleteAll();
    }

    public Car create(String brand, String model, Region region, Integer stock) {
        Car car = new Car(brand, model, region, stock);
        try {
            car = carRepository.save(car);
            return car;
        } catch (IllegalArgumentException e) {
            System.out.println("Car already exists with that name!");
            return null;
        }
    }

    public List<Car> find(String brand, String model, String region, Integer stock) {
        List<Car> cars = carRepository.findAll();

        if (brand != null)
            cars = cars.stream().filter(car -> car.getBrand().equals(brand)).collect(Collectors.toList());
        if (model != null)
            cars = cars.stream().filter(car -> car.getModel().equals(model)).collect(Collectors.toList());
        if (region != null)
            cars = cars.stream().filter(car -> car.getRegion().getName().equals(region)).collect(Collectors.toList());
        if (stock != null)
            cars = cars.stream().filter(car -> car.getStock().equals(stock)).collect(Collectors.toList());
        return cars;
    }
}
