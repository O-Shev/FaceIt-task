package com.leshkins.faceittask.controller;

import com.leshkins.faceittask.dto.JobDTO;
import com.leshkins.faceittask.model.Job;
import com.leshkins.faceittask.service.JobService;
import com.leshkins.faceittask.util.mapper.JobMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class JobControllerTest {

    @Mock
    private JobService jobService;

    @Mock
    private JobMapper jobMapper;

    @InjectMocks
    private JobController jobController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

    @Test
    void testGetJobs() throws Exception {
        Job job = new Job(); // Assuming Job has appropriate constructors or setters
        var jobDTO = new JobDTO("slug", "company", "title", "description", true, "url",
                List.of("tag1", "tag2"), List.of("full-time"), "location", System.currentTimeMillis());
        when(jobService.getJobs(0, 50)).thenReturn(List.of(job));
        when(jobMapper.toDto(any(Job.class))).thenReturn(jobDTO);

        mockMvc.perform(get("/job/all?page=0&size=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("slug"))
                .andExpect(jsonPath("$[0].company_name").value("company"))
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].description").value("description"))
                .andExpect(jsonPath("$[0].remote").value(true))
                .andExpect(jsonPath("$[0].url").value("url"))
                .andExpect(jsonPath("$[0].tags[0]").value("tag1"))
                .andExpect(jsonPath("$[0].job_types[0]").value("full-time"))
                .andExpect(jsonPath("$[0].location").value("location"))
                .andExpect(jsonPath("$[0].created_at").value(jobDTO.created_at()));
    }

    @Test
    void testGetTopJobs() throws Exception {
        Job job = new Job(); // Assuming Job has appropriate constructors or setters
        JobDTO jobDTO = new JobDTO("slug", "company", "title", "description", true, "url",
                List.of("tag1", "tag2"), List.of("full-time"), "location", System.currentTimeMillis());
        when(jobService.getTopJobs()).thenReturn(List.of(job));
        when(jobMapper.toDto(any(Job.class))).thenReturn(jobDTO);

        mockMvc.perform(get("/job/top"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("slug"))
                .andExpect(jsonPath("$[0].company_name").value("company"))
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].description").value("description"))
                .andExpect(jsonPath("$[0].remote").value(true))
                .andExpect(jsonPath("$[0].url").value("url"))
                .andExpect(jsonPath("$[0].tags[0]").value("tag1"))
                .andExpect(jsonPath("$[0].job_types[0]").value("full-time"))
                .andExpect(jsonPath("$[0].location").value("location"))
                .andExpect(jsonPath("$[0].created_at").value(jobDTO.created_at()));
    }
}
