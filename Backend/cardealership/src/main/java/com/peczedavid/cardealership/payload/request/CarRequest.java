package com.peczedavid.cardealership.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {
    @NotBlank
    @Size(max = 50)
	private String brand;
    
    @NotBlank
    @Size(max = 50)
	private String model;

	private Set<String> region;

    private Integer stock;
}
