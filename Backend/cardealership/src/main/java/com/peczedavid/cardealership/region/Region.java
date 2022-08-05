package com.peczedavid.cardealership.region;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.peczedavid.cardealership.car.Car;
import com.peczedavid.cardealership.user.User;

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

    @Column(unique = true, nullable = false)
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region", orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region", orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    public Region(String name) {
        this.name = name;
    }

    public Region(String name, List<Car> cars, List<User> users) {
        this.name = name;
        this.cars = cars;
        this.users = users;
    }
}
