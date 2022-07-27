package com.peczedavid.cardealership.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peczedavid.cardealership.models.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	Optional<Car> findByBrand(String brand);
}