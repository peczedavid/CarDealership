package com.peczedavid.cardealership.car;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peczedavid.cardealership.car.payload.CarRequest;
import com.peczedavid.cardealership.region.Region;
import com.peczedavid.cardealership.region.RegionRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RegionRepository regionRepository;

    public Car findById(Integer id) {
        Car car = carRepository.findById((long) id).orElse(null);
        if(car == null) return null;
        return car;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public void deleteAll() {
        carRepository.deleteAll();
    }

    public Car update(Integer id, CarRequest carRequest) {
        // TODO: check if already exists
        Car car = carRepository.findById((long) id).orElse(null);
        if (car == null) return null;
        Region region = regionRepository.findByName(carRequest.getRegion()).orElse(null);
        if (region == null)
            throw new RuntimeException("Can't update car, region " + carRequest.getRegion() + " not found!");
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setStock(carRequest.getStock());
        car.setRegion(region);
        return carRepository.save(car);
    }

    public List<Car> create(List<CarRequest> carRequests) {
        List<Car> cars = new ArrayList<Car>(carRequests.size());
        for (CarRequest request : carRequests) {
            Car car = this.create(request.getBrand(), request.getModel(), request.getRegion(), request.getStock());
            if (car == null)
                throw new RuntimeException("Error while creating car");
            cars.add(car);
        }
        return cars;
    }

    public Car create(String brand, String model, String regionName, Integer stock) {
        Region region = regionRepository.findByName(regionName).orElse(null);
        if (region == null)
            throw new RuntimeException("Can't create car, region " + regionName + " not found!");
        return this.create(brand, model, region, stock);
    }

    private Car create(String brand, String model, Region region, Integer stock) {
        Car car = new Car(brand, model, region, stock);
        try {
            car = carRepository.save(car);
            return car;
        } catch (IllegalArgumentException e) {
            System.out.println(e);
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
