package com.leshkins.faceittask.controller;

import com.leshkins.faceittask.dto.JobDTO;
import com.leshkins.faceittask.service.JobService;
import com.leshkins.faceittask.util.mapper.JobMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;
    private final JobMapper jobMapper;

    @Autowired
    public JobController(JobService jobService, JobMapper jobMapper) {
        this.jobService = jobService;
        this.jobMapper = jobMapper;
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<JobDTO>> getJobs(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                @RequestParam(defaultValue = "50") @Min(1) @Max(100) int size){

        List<JobDTO> jobDTOList = jobService.getJobs(page, size).stream().map(jobMapper::toDto).toList();
        return ResponseEntity.ok(jobDTOList);
    }

    @GetMapping("/top")
    @ResponseBody
    public ResponseEntity<List<JobDTO>> getTopJobs(){

        List<JobDTO> jobDTOList = jobService.getTopJobs().stream().map(jobMapper::toDto).toList();
        return ResponseEntity.ok(jobDTOList);
    }

}
