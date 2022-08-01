package com.peczedavid.cardealership.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peczedavid.cardealership.models.Car;
import com.peczedavid.cardealership.repositories.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car getCarById(int id) {
        return carRepository.findById((long) id).orElse(null);
    }

    public List<Car> getCarsByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

    public List<Car> getCarsByModel(String model) {
        return carRepository.findByModel(model);
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public Car deleteById(Long id) {
        Car dbCar = carRepository.findById(id).orElse(null);
        Car tmpCar = new Car();
        if (dbCar != null) {
            tmpCar.setId(id);
            tmpCar.setBrand(dbCar.getBrand());
            tmpCar.setModel(dbCar.getModel());
            tmpCar.setRegions(dbCar.getRegions());
            carRepository.deleteById(id);
            return tmpCar;
        }
        return null;
    }

    public Car updateCar(Long id, Car car) {
        Car dbCar = carRepository.findById(id).orElse(null);
        if (dbCar != null) {
            dbCar = car;
            carRepository.save(dbCar);
            return dbCar;
        }
        return null;
    }

    public void deleteAll() {
        carRepository.deleteAll();
    }
}
