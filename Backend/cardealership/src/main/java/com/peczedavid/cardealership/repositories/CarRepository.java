package com.peczedavid.cardealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peczedavid.cardealership.models.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
}