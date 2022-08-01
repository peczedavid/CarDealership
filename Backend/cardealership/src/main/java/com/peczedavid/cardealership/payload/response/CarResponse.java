package com.peczedavid.cardealership.payload.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarResponse {
    private Long id;
    private String brand;
    private String model;
    private List<String> regions;
    private Integer stock;

    public CarResponse(Long id, String brand, String model, Set<String> regions, Integer stock) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        List<String> strRegions = new ArrayList<String>(regions.size());
        for(String s : regions)
            strRegions.add(s);
        this.regions = strRegions;
        this.stock = stock;
    }
}
