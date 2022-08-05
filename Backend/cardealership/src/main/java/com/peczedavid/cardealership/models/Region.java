package com.peczedavid.cardealership.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region", orphanRemoval = true)
    //@JoinColumn(name = "car_id", referencedColumnName = "id")
    private List<Car> cars = new ArrayList<>();

    public Region(String name) {
        this.name = name;
    }

    public Region(String name, List<Car> cars) {
        this.name = name;
        this.cars = cars;
    }
}
