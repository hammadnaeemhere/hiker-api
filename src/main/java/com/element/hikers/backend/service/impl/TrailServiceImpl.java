package com.element.hikers.backend.service.impl;

import com.element.hikers.backend.dto.TrailDto;
import com.element.hikers.backend.entity.Trail;
import com.element.hikers.backend.mappers.TrailMapper;
import com.element.hikers.backend.repository.TrailRepository;
import com.element.hikers.backend.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrailServiceImpl implements TrailService {

    private final TrailRepository trailRepository;

    @Autowired
    public TrailServiceImpl(TrailRepository trailRepository) {
        this.trailRepository = trailRepository;
    }


    @Override
    public List<TrailDto> getAllTrails() {
        return trailRepository.findAllTrails();
    }

    @Override
    public TrailDto save(TrailDto trail) {
        return TrailMapper.trailToTrailDto(trailRepository.save(TrailMapper.trailDtoToTrail(trail)));
    }

    @Override
    public TrailDto update(Long trailId, TrailDto trail) {
        Trail trailEntity = TrailMapper.trailDtoToTrail(trail);
        trailEntity.setTrailId(trailId);
        return TrailMapper.trailToTrailDto(trailRepository.save(trailEntity));
    }
}
