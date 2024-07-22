package com.leshkins.faceittask.util.mapper;

import com.leshkins.faceittask.dto.JobDTO;
import com.leshkins.faceittask.model.Job;
import com.leshkins.faceittask.model.Location;
import com.leshkins.faceittask.repository.LocationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class JobMapper {


    @Autowired
    private LocationRepository locationRepository;


    @Mapping(target = "jobTypes", source = "job_types")
    @Mapping(target = "createdAt", source = "created_at")
    @Mapping(target = "companyName", source = "company_name")
    @Mapping(source = "location", target = "location", qualifiedByName = "locationNameToLocation")
    public abstract Job toModel(JobDTO jobDTO);

    @Mapping(target = "job_types", source = "jobTypes")
    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "company_name", source = "companyName")
    @Mapping(source = "location.name", target = "location")
    public abstract JobDTO toDto(Job job);


    @Named("locationNameToLocation")
    Location locationNameToLocation(String locationName) {
        if (locationName == null) {
            return null;
        }
        Location location = locationRepository.findByName(locationName);
        if (location == null) {
            location = new Location();
            location.setName(locationName);
            locationRepository.save(location);
        }
        return location;
    }
}
