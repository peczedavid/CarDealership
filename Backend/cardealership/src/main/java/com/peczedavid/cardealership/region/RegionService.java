package com.peczedavid.cardealership.region;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> findAll(String name) {
        List<Region> regions = regionRepository.findAll();

        if (name != null)
            regions = regions.stream().filter(region -> region.getName().equals(name)).collect(Collectors.toList());

        return regions;
    }

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public void deleteAll() {
        regionRepository.deleteAll();
    }

    public Region findByName(String name) {
        Optional<Region> region = regionRepository.findByName(name);
        if (region.isPresent())
            return region.get();
        return null;
    }

    public Region deleteById(Integer _id) {
        Long id = new Long((long) _id);
        Region region = regionRepository.findById(id).orElse(null);
        if(region == null) return null;
        regionRepository.delete(region);
        return region;
    }

    public List<Region> findByName() {
        return regionRepository.findAll();
    }

    /**
     * @return The created entity object with it's id or null if a region already
     *         exists with given name.
     */
    public Region create(String regionName) {
        if (regionRepository.existsByName(regionName))
            return null;

        Region region = new Region(regionName);
        return regionRepository.save(region);
    }

    /**
     * @return The updated entity object with it's id or null if a region already
     *         exists with given name or no region was found with the given id.
     */
    public Region update(Integer id, String newRegionName) {
        if (regionRepository.existsByName(newRegionName))
            return null;

        Region region = regionRepository.findById((long) id).orElse(null);
        if (region == null)
            return null;
        region.setName(newRegionName);
        return regionRepository.save(region);
    }
}
