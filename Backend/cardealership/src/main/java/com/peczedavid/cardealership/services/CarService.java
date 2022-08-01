package com.peczedavid.cardealership.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peczedavid.cardealership.models.Car;
import com.peczedavid.cardealership.models.ERegion;
import com.peczedavid.cardealership.models.Region;
import com.peczedavid.cardealership.payload.request.CarRequest;
import com.peczedavid.cardealership.repositories.CarRepository;
import com.peczedavid.cardealership.repositories.RegionRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RegionRepository regionRepository;

    public Car update(int id, CarRequest carRequest) {
        Car car = carRepository.findById((long) id).orElse(null);
        if(car == null) return null;

        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        Integer stock = carRequest.getStock();
        car.setStock(stock == null ? 0 : stock);
        Set<Region> regions = new HashSet<Region>();
        carRequest.getRegions().forEach(regionStr -> {
            Region region = regionRepository.findByName(ERegion.fromString(regionStr))
                .orElseThrow(() -> new RuntimeException("Region " + regionStr + " not found"));
            regions.add(region);
        });
        car.setRegions(regions);
        carRepository.save(car);
        return car;
    }

    public Car save(CarRequest carRequest) {
        // TODO: can't add same exact car
        Car car = new Car(carRequest.getBrand(), carRequest.getModel());
        Integer stock = carRequest.getStock();
        car.setStock(stock == null ? 0 : stock);
        Set<Region> regions = new HashSet<Region>();
        carRequest.getRegions().forEach(regionStr -> {
            Region region = regionRepository.findByName(ERegion.fromString(regionStr))
                .orElseThrow(() -> new RuntimeException("Region " + regionStr + " not found"));
            regions.add(region);
        });
        car.setRegions(regions);
        carRepository.save(car);
        return car;
    }

    public List<Car> getCars(String brand, String model, String region, Integer stock) {
        List<Car> cars = carRepository.findAll();

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

        return cars;
    }

    public Car getById(int id) {
        return carRepository.findById((long) id).orElse(null);
    }  

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Car deleteById(int _id) {
        Long id = (long)_id;
        Car dbCar = carRepository.findById(id).orElse(null);
        if (dbCar != null) {
            Car tmpCar = new Car(dbCar);
            carRepository.deleteById(id);
            return tmpCar;
        }
        return null;
    }

    public void deleteAll() {
        carRepository.deleteAll();
    }
}
