package com.peczedavid.cardealership.payload.response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.peczedavid.cardealership.models.Car;

public class CarResponse {
    private Long id;
    private String brand;
    private String model;
    private List<String> regions;

    public CarResponse(Long id, String brand, String model, Set<String> regions) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        List<String> strRegions = new ArrayList<String>(regions.size());
        for(String s : regions)
            strRegions.add(s);
        this.regions = strRegions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }
}
