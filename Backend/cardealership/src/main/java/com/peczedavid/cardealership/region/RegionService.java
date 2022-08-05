package com.peczedavid.cardealership.region;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    
    @Autowired
    private RegionRepository regionRepository;

    public void deleteAll() {
        regionRepository.deleteAll();
    }

    public Region findByName(String name) {
        Optional<Region> region = regionRepository.findByName(name);
        if(region.isPresent()) return region.get();
        return null;
    }

    public List<Region> findByName() {
        return regionRepository.findAll();
    }

    public Region create(String regionName) {
        Region region = new Region(regionName);
        try {
            region = regionRepository.save(region);
            return region;
        } catch (IllegalArgumentException e) {
            System.out.println("Region already exists with that name!");
            return null;
        }
    }
}
