package com.peczedavid.cardealership.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peczedavid.cardealership.models.ERegion;
import com.peczedavid.cardealership.models.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
	Optional<Region> findByName(ERegion name);
}