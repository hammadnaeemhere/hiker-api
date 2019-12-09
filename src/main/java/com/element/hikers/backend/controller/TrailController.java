package com.element.hikers.backend.controller;

import com.element.hikers.backend.dto.ResponseDto;
import com.element.hikers.backend.dto.TrailDto;
import com.element.hikers.backend.service.TrailService;
import com.element.hikers.backend.validators.ValidTrailId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Slf4j
public class TrailController {

    @Autowired
    private TrailService trailService;

    @GetMapping("/trails")
    public List<TrailDto> getTrails() {
        log.debug("executing get trails");
        return trailService.getAllTrails();
    }

    @PostMapping("/auth/trails")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody TrailDto trail) {
        log.debug("executing creating trail: {}", trail);
        ResponseDto responseDTO = ResponseDto.builder()
                .message("Trail has been created.")
                .body(trailService.save(trail)).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/auth/trails/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable @ValidTrailId Long id, @Valid @RequestBody TrailDto trail) {
        ResponseDto responseDTO = ResponseDto.builder()
                .message("Trail has been updated.")
                .body(trailService.update(id, trail)).build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }
}
