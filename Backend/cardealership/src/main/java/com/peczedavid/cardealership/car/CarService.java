package com.peczedavid.cardealership.car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (car == null)
            return null;
        return car;
    }

    public boolean deletById(Integer id) {
        if (!carRepository.existsById((long) id))
            return false;
        carRepository.deleteById((long) id);
        return true;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public void deleteAll() {
        carRepository.deleteAll();
    }

    public Car update(Integer id, CarRequest carRequest) {
        Car car = carRepository.findById((long) id).orElse(null);
        if (car == null)
            return null;
        Region region = regionRepository.findByName(carRequest.getRegion()).orElse(null);
        if (region == null)
            throw new RuntimeException("Can't update car, region " + carRequest.getRegion() + " not found!");
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setStock(carRequest.getStock());
        car.setDescription(carRequest.getDescription());
        car.setRegion(region);
        return carRepository.save(car);
    }

    public List<Car> create(List<CarRequest> carRequests) {
        List<Car> cars = new ArrayList<Car>(carRequests.size());
        for (CarRequest request : carRequests) {
            Car car = this.create(request.getBrand(), request.getModel(), request.getRegion(), request.getStock(),
                    request.getDescription());
            if (car == null)
                throw new RuntimeException("Error while creating car");
            cars.add(car);
        }
        return cars;
    }

    public Car create(String brand, String model, String regionName, Integer stock, String description) {
        Region region = regionRepository.findByName(regionName).orElse(null);
        if (region == null)
            throw new RuntimeException("Can't create car, region " + regionName + " not found!");
        return this.create(brand, model, region, stock, description);
    }

    private Car create(String brand, String model, Region region, Integer stock, String description) {
        Car car = new Car(brand, model, region, stock, description);
        try {
            car = carRepository.save(car);
            return car;
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Car> find(String brand, String model, String region, Integer stock, String description, String sort) {
        Stream<Car> cars = carRepository.findAll().stream();

        if (brand != null)
            cars = cars.filter(car -> car.getBrand().toUpperCase().contains(brand.toUpperCase()));
        if (model != null)
            cars = cars.filter(car -> car.getModel().toUpperCase().contains(model.toUpperCase()));
        if (region != null)
            cars = cars.filter(car -> car.getRegion().getName().toUpperCase().contains(region.toUpperCase()));
        if (stock != null)
            cars = cars.filter(car -> car.getStock().equals(stock));
        if (description != null)
            cars = cars.filter(car -> car.getDescription().toUpperCase().contains(description.toUpperCase()));

        switch (sort) {
            case "brand-a-z":
                cars = cars.sorted((car1, car2) -> car1.getBrand().compareTo(car2.getBrand()));
                break;
            case "brand-z-a":
                cars = cars.sorted((car1, car2) -> car2.getBrand().compareTo(car1.getBrand()));
                break;
            case "stock-asc":
                cars = cars.sorted(Comparator.comparingInt(Car::getStock));
                break;
            case "stock-desc":
                cars = cars.sorted(Comparator.comparingInt(Car::getStock).reversed());
                break;
            default:
                break;
        }

        return cars.collect(Collectors.toList());
    }
}
