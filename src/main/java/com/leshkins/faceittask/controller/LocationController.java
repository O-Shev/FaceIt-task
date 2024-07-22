package com.leshkins.faceittask.controller;


import com.leshkins.faceittask.dto.JobDTO;
import com.leshkins.faceittask.dto.LocationStatsDTO;
import com.leshkins.faceittask.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<List<LocationStatsDTO>> getJobs(){

        return ResponseEntity.ok().body(locationService.getLocationsStats());
    }
}
