package com.leshkins.faceittask.service;

import com.leshkins.faceittask.dto.LocationStatsDTO;
import com.leshkins.faceittask.model.Job;
import com.leshkins.faceittask.model.Location;
import com.leshkins.faceittask.repository.LocationRepository;
import com.leshkins.faceittask.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        locationService = new LocationService(locationRepository);
    }

    @Test
    void testGetLocationsStats() {
        // Create mock data
        Job job1 = new Job();
        Job job2 = new Job();
        Location location1 = new Location();
        location1.setName("Location1");
        location1.setJobs(List.of(job1));
        Location location2 = new Location();
        location2.setName("Location2");
        location2.setJobs(List.of(job2, job1));

        // Mock repository behavior
        when(locationRepository.findAll()).thenReturn(List.of(location1, location2));

        // Call the service method
        List<LocationStatsDTO> stats = locationService.getLocationsStats();

        // Verify the results
        assertEquals(2, stats.size());
        assertEquals("Location1", stats.get(0).location());
        assertEquals(1, stats.get(0).jobs_number());
        assertEquals("Location2", stats.get(1).location());
        assertEquals(2, stats.get(1).jobs_number());
    }
}
