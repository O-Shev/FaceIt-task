package com.leshkins.faceittask.repository;

import com.leshkins.faceittask.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByName(String name);
}
