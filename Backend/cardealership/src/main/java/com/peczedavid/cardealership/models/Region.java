package com.peczedavid.cardealership.models;

import javax.persistence.*;

import com.peczedavid.cardealership.repositories.RegionRepository;

@Entity
@Table(name = "regions")
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 40)
	private ERegion name;

	public Region() {

	}

	public Region(ERegion name) {
		this.name = name;
	}
    
	public Integer getId() {
		return id;
	}
    
	public void setId(Integer id) {
		this.id = id;
	}

	public ERegion getName() {
		return name;
	}

	public void setName(ERegion name) {
		this.name = name;
	}
}