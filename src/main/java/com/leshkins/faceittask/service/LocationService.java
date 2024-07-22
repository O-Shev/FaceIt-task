package com.leshkins.faceittask.service;

import com.leshkins.faceittask.dto.LocationStatsDTO;
import com.leshkins.faceittask.model.Location;
import com.leshkins.faceittask.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationStatsDTO> getLocationsStats () {
        return locationRepository.findAll().stream()
                .map(l -> new LocationStatsDTO(l.getName(), l.getJobs().size()))
                .toList();
    }
}
