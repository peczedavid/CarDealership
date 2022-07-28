package com.peczedavid.cardealership.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String brand;

    @NotBlank
	@Size(max = 40)
	private String model;

    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "car_regions", 
			   joinColumns = @JoinColumn(name = "car_id"), 
			   inverseJoinColumns = @JoinColumn(name = "region_id"))
	private Set<Region> regions = new HashSet<>();

    public Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public Car() {
        
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

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }
    
}