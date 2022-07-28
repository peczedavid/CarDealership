package com.peczedavid.cardealership.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CarRequest {

    @NotBlank
    @Size(max = 50)
	private String brand;
    
    @NotBlank
    @Size(max = 50)
	private String model;

	private Set<String> region;

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

    public Set<String> getRegion() {
        return region;
    }

    public void setRegion(Set<String> region) {
        this.region = region;
    }
}
