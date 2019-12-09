package com.element.hikers.backend.repository;

import com.element.hikers.backend.dto.TrailDto;
import com.element.hikers.backend.entity.Trail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrailRepository extends CrudRepository<Trail, Long> {

    @Query(value = "SELECT new com.element.hikers.backend.dto.TrailDto(" +
            "t.trailId, " +
            "t.name, " +
            "t.startTime, " +
            "t.endTime, " +
            "t.minAge, " +
            "t.maxAge, " +
            "t.ticketPrice, " +
            "t.currency) " +
            "FROM Trail t")
    List<TrailDto> findAllTrails();
}
