package com.peczedavid.cardealership.car.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {
    
    private String brand;
    private String model;
    private String region;
    private Integer stock;
    private String description;
}
