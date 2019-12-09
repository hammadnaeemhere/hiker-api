package com.element.hikers.backend.service;

import com.element.hikers.backend.dto.TrailDto;

import java.util.List;

public interface TrailService {

    List<TrailDto> getAllTrails();

    TrailDto save(TrailDto trail);

    TrailDto update(Long trailId, TrailDto trail);
}
