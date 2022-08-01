package com.peczedavid.cardealership.models;

import javax.persistence.*;

import lombok.ToString;

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

	public static Region fromString(String region) {
		return new Region(ERegion.fromString(region));
	}

	@Override
	public boolean equals(Object obj) {
		// null check
		if (obj == null) return false;
		// self check
		if (this == obj) return true;
		// type check and cast
		if (this.getClass() != obj.getClass()) return false;
		
		Region region = (Region) obj;
		// field comparison
		return region.name.toString().equals(region.getName().toString());
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