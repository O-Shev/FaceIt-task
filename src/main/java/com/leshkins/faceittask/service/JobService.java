package com.leshkins.faceittask.service;

import com.leshkins.faceittask.api.ArbeitnowApi;
import com.leshkins.faceittask.dto.JobDTO;
import com.leshkins.faceittask.model.Job;
import com.leshkins.faceittask.model.Location;
import com.leshkins.faceittask.repository.JobRepository;
import com.leshkins.faceittask.repository.LocationRepository;
import com.leshkins.faceittask.util.mapper.JobMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class JobService {
    private final ArbeitnowApi arbeitnowApi;
    private final JobMapper jobMapper;
    private final JobRepository jobRepository;


    @Autowired
    public JobService(ArbeitnowApi arbeitnowApi, JobMapper jobMapper, JobRepository jobRepository) {
        this.arbeitnowApi = arbeitnowApi;
        this.jobMapper = jobMapper;
        this.jobRepository = jobRepository;
    }

    @PostConstruct
    public void onApplicationStart() {
        updateDB();
    }

    @Scheduled(fixedRate = 10000)
    public void updateDB(){
        jobRepository.deleteAll();
        for(int page = 1; page <= 5; page++){
            jobRepository.saveAll(getJobsFromArbeitnow(page).stream()
                    .map(jobMapper::toModel)
                    .toList());
        }
    }





    public List<JobDTO> getJobsFromArbeitnow(int page){
        return arbeitnowApi.jobBoardApi(page).data();
    }

    public List<Job> getJobs(int pageNumber, int pageSize){
        return jobRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending())).getContent();
    }

    // Arbeitnow doesn't provide any info in Api that we can use to establish popularity or else
    // so return just last 10 newest
    public List<Job> getTopJobs() {
        return getJobs(0, 10);
    }
}
