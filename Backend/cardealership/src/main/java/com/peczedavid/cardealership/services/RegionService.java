package com.peczedavid.cardealership.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peczedavid.cardealership.models.ERegion;
import com.peczedavid.cardealership.models.Region;
import com.peczedavid.cardealership.payload.request.RegionRequest;
import com.peczedavid.cardealership.repositories.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getRoles(String name) {
        List<Region> regions = regionRepository.findAll();

        if (name != null) {
            regions = regions.stream()
                    .filter(region -> region.getName() == ERegion.fromString(name))
                    .collect(Collectors.toList());
        }
        return regions;
    }

    public Region save(RegionRequest regionRequest) {
        // TODO: Can't have 2 regions with the same name
        ERegion eRegion = ERegion.fromString(regionRequest.getName());
        if(eRegion == null) return null;
        Region region = new Region(eRegion);
        regionRepository.save(region);
        return region;
    }

    public Region deleteById(Integer id) {
        Region region = regionRepository.findById(id).orElse(null);
        if(region == null) return null;
        regionRepository.delete(region);
        return region;
    }
}
