package com.leshkins.faceittask.controller;

import com.leshkins.faceittask.dto.LocationStatsDTO;
import com.leshkins.faceittask.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    void testGetLocationsStats() throws Exception {
        LocationStatsDTO dto1 = new LocationStatsDTO("Location1", 1);
        LocationStatsDTO dto2 = new LocationStatsDTO("Location2", 2);
        when(locationService.getLocationsStats()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/location/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location").value("Location1"))
                .andExpect(jsonPath("$[0].jobs_number").value(1))
                .andExpect(jsonPath("$[1].location").value("Location2"))
                .andExpect(jsonPath("$[1].jobs_number").value(2));
    }
}
