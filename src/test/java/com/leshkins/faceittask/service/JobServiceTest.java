package com.leshkins.faceittask.service;

import com.leshkins.faceittask.api.ArbeitnowApi;
import com.leshkins.faceittask.dto.ArbeitnowJobBoardApiDTO;
import com.leshkins.faceittask.dto.JobDTO;
import com.leshkins.faceittask.model.Job;
import com.leshkins.faceittask.repository.JobRepository;
import com.leshkins.faceittask.service.JobService;
import com.leshkins.faceittask.util.mapper.JobMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private ArbeitnowApi arbeitnowApi;

    @Mock
    private JobMapper jobMapper;

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        jobService = new JobService(arbeitnowApi, jobMapper, jobRepository);
    }

    @Test
    void testUpdateDB() {
        JobDTO jobDTO = new JobDTO("slug", "company", "title", "description", true, "url",
                List.of("tag1", "tag2"), List.of("full-time"), "location", System.currentTimeMillis());
        Job job = new Job();
        when(arbeitnowApi.jobBoardApi(anyInt())).thenReturn(new ArbeitnowJobBoardApiDTO(List.of(jobDTO), null, null));
        when(jobMapper.toModel(any(JobDTO.class))).thenReturn(job);

        jobService.updateDB();

        verify(jobRepository, times(1)).deleteAll();
        verify(jobRepository, times(5)).saveAll(anyList());
    }

    @Test
    void testGetJobs() {
        Job job = new Job();
        Page<Job> page = new PageImpl<>(List.of(job));
        when(jobRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<Job> jobs = jobService.getJobs(0, 10);

        assertEquals(1, jobs.size());
        assertEquals(job, jobs.get(0));
    }

    @Test
    void testGetTopJobs() {
        Job job = new Job();
        Page<Job> page = new PageImpl<>(List.of(job));
        when(jobRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<Job> jobs = jobService.getTopJobs();

        assertEquals(1, jobs.size());
        assertEquals(job, jobs.get(0));
    }

    @Test
    void testGetJobsFromArbeitnow() {
        JobDTO jobDTO = new JobDTO("slug", "company", "title", "description", true, "url",
                List.of("tag1", "tag2"), List.of("full-time"), "location", System.currentTimeMillis());
        when(arbeitnowApi.jobBoardApi(anyInt())).thenReturn(new ArbeitnowJobBoardApiDTO(List.of(jobDTO), null, null));

        List<JobDTO> jobDTOs = jobService.getJobsFromArbeitnow(1);

        assertEquals(1, jobDTOs.size());
        assertEquals(jobDTO, jobDTOs.get(0));
    }
}
