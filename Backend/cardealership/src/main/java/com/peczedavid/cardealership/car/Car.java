package com.peczedavid.cardealership.car;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.peczedavid.cardealership.region.Region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false)
    private String model;

    // If CascadeType.ALL then can't delete
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id", nullable = false, referencedColumnName = "id")
    private Region region;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String description;

    public Car(String brand, String model, Region region, Integer stock, String description) {
        this.brand = brand;
        this.model = model;
        this.region = region;
        this.stock = stock;
        this.description = description;
    }
}
