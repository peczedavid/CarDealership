package com.peczedavid.cardealership.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peczedavid.cardealership.models.Region;
import com.peczedavid.cardealership.payloads.region.RegionRequest;
import com.peczedavid.cardealership.services.RegionService;

@CrossOrigin(origins = { "http://localhost:8081" }, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateRegion(@PathVariable Integer id) {
        Region region = regionService.deleteById(id);
        if (region == null)
            return ResponseEntity.badRequest().build();
        return new ResponseEntity<String>("Deleted region with id " + region.getId(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Region>> getRegions(@RequestParam(name = "name", required = false) String name) {
        List<Region> regions = regionService.find(name);
        return ResponseEntity.ok(regions);
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createRegions(@RequestBody List<RegionRequest> regionRequests) {
        List<Region> regions = regionService.create(regionRequests);
        return new ResponseEntity<List<Region>>(regions, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> createRegion(@RequestBody RegionRequest regionRequest) {
        Region region = regionService.create(regionRequest.getName());
        if (region == null)
            return new ResponseEntity<String>("Region already exists with given name!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Region>(region, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegion(@PathVariable Integer id, @RequestBody RegionRequest regionRequest) {
        Region region = regionService.update(id, regionRequest.getName());
        if (region == null)
            return new ResponseEntity<String>("Failed to update region!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Region>(region, HttpStatus.OK);
    }
}
