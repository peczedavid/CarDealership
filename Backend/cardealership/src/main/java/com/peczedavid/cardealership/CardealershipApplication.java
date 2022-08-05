package com.peczedavid.cardealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.peczedavid.cardealership.car.Car;
import com.peczedavid.cardealership.car.CarService;
import com.peczedavid.cardealership.region.Region;
import com.peczedavid.cardealership.region.RegionService;
import com.peczedavid.cardealership.user.User;
import com.peczedavid.cardealership.user.UserService;

@SpringBootApplication
public class CardealershipApplication {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext cac = SpringApplication.run(CardealershipApplication.class, args);

		// CarService carService = cac.getBean(CarService.class);
		// RegionService regionService = cac.getBean(RegionService.class);
		// UserService userService = cac.getBean(UserService.class);

		// carService.deleteAll();
		// regionService.deleteAll();
		// userService.deleteAll();

		// Region regionHungary = regionService.create("Hungary");
		// Region regionGermany = regionService.create("Germany");

		// Car skodaOctavia = carService.create("Skoda", "Octavia", regionHungary);
		// Car renaultTalia = carService.create("Renault", "Talia", regionHungary);
		// Car volkswagenGolf = carService.create("Volkswagen", "Golf", regionGermany);
		// User adam = userService.create("adam", "adam@a.com", "secret123", false, regionHungary);
		// User root = userService.create("root", "root@a.com", "root", true, regionGermany);
		
		// regionHungary.getCars().add(skodaOctavia);
		// regionHungary.getCars().add(renaultTalia);
		// regionGermany.getCars().add(volkswagenGolf);
		// regionGermany.getUsers().add(root);
		// regionHungary.getUsers().add(adam);
	}

}
