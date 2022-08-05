package com.peczedavid.cardealership.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.models.Region;
import com.peczedavid.cardealership.payload.request.RegionRequest;
import com.peczedavid.cardealership.services.RegionService;

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/regions")
public class RegionController {
    
    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> getRegions(@RequestParam(name = "name", required = false) String name) {
        List<Region> regions = regionService.getRoles(name);
        if(regions.size() > 0) return ResponseEntity.ok(regions);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Region> saveRegion(@RequestBody RegionRequest regionRequest) {
        Region region = regionService.save(regionRequest);
        if(region != null) return ResponseEntity.ok(region);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Region> deleteRegion(@PathVariable Integer id) {
        Region region = regionService.deleteById(id);
        if(region != null) return ResponseEntity.ok(region);
        return ResponseEntity.notFound().build();
    }
}
