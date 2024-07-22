package com.leshkins.faceittask.dto;

import java.util.List;

public record JobDTO(
        String slug,
        String company_name,
        String title,
        String description,
        Boolean remote,
        String url,
        List<String> tags,
        List<String> job_types,
        String location,
        Long created_at
) {}
