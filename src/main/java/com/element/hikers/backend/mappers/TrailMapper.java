package com.element.hikers.backend.mappers;

import com.element.hikers.backend.dto.TrailDto;
import com.element.hikers.backend.entity.Trail;

public class TrailMapper {

    public static Trail trailDtoToTrail(TrailDto trailDto) {
        Trail trail = new Trail();
        trail.setName(trailDto.getName());
        trail.setStartTime(trailDto.getStartTime());
        trail.setEndTime(trailDto.getEndTime());
        trail.setMinAge(trailDto.getMinAge());
        trail.setMaxAge(trailDto.getMaxAge());
        trail.setTicketPrice(trailDto.getTicketPrice());
        trail.setCurrency(trailDto.getCurrency());

        return trail;
    }

    public static TrailDto trailToTrailDto(Trail trail) {
        TrailDto trailDto = new TrailDto();
        trailDto.setName(trail.getName());
        trailDto.setTrailId(trail.getTrailId());
        trailDto.setStartTime(trail.getStartTime());
        trailDto.setEndTime(trail.getEndTime());
        trailDto.setMinAge(trail.getMinAge());
        trailDto.setMaxAge(trail.getMaxAge());
        trailDto.setTicketPrice(trail.getTicketPrice());
        trailDto.setCurrency(trail.getCurrency());

        return trailDto;
    }
}
