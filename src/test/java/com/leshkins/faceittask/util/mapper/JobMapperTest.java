package com.leshkins.faceittask.util.mapper;

import com.leshkins.faceittask.dto.JobDTO;
import com.leshkins.faceittask.model.Job;
import com.leshkins.faceittask.model.Location;
import com.leshkins.faceittask.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobMapperTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private JobMapper jobMapper = new JobMapperImpl();


    @Test
    void testToModel() {
        JobDTO jobDTO = new JobDTO("slug", "company_name", "title", "description", true, "url",
                List.of("tag1", "tag2"), List.of("full-time"), "LocationName", System.currentTimeMillis());

        Location location = new Location();
        location.setName("LocationName");
        when(locationRepository.findByName("LocationName")).thenReturn(location);

        Job job = jobMapper.toModel(jobDTO);

        assertNotNull(job);
        assertEquals("slug", job.getSlug());
        assertEquals("company_name", job.getCompanyName());
        assertEquals("title", job.getTitle());
        assertEquals("description", job.getDescription());
        assertTrue(job.getRemote());
        assertEquals("url", job.getUrl());
        assertEquals(List.of("tag1", "tag2"), job.getTags());
        assertEquals(List.of("full-time"), job.getJobTypes());
        assertNotNull(job.getLocation());
        assertEquals("LocationName", job.getLocation().getName());
        assertEquals(jobDTO.created_at(), job.getCreatedAt());
    }

    @Test
    void testToDto() {
        Job job = new Job();
        job.setSlug("slug");
        job.setCompanyName("company_name");
        job.setTitle("title");
        job.setDescription("description");
        job.setRemote(true);
        job.setUrl("url");
        job.setTags(List.of("tag1", "tag2"));
        job.setJobTypes(List.of("full-time"));

        Location location = new Location();
        location.setName("LocationName");
        job.setLocation(location);

        JobDTO jobDTO = jobMapper.toDto(job);

        assertNotNull(jobDTO);
        assertEquals("slug", jobDTO.slug());
        assertEquals("company_name", jobDTO.company_name());
        assertEquals("title", jobDTO.title());
        assertEquals("description", jobDTO.description());
        assertTrue(jobDTO.remote());
        assertEquals("url", jobDTO.url());
        assertEquals(List.of("tag1", "tag2"), jobDTO.tags());
        assertEquals(List.of("full-time"), jobDTO.job_types());
        assertEquals("LocationName", jobDTO.location());
        assertEquals(job.getCreatedAt(), jobDTO.created_at());
    }
}
