package com.element.hikers.backend.service.impl;

import com.element.hikers.backend.dto.TrailDto;
import com.element.hikers.backend.entity.Trail;
import com.element.hikers.backend.repository.TrailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrailServiceImplTest {

    @Mock
    private TrailRepository trailRepository;

    @InjectMocks
    private TrailServiceImpl trailService;

    @DisplayName("Test getAll with all possible params.")
    @Test
    void testGetAll() {
        //prepare mocks
        TrailDto trail1 = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        TrailDto trail2 = new TrailDto(2L, "Trail2", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        when(trailRepository.findAllTrails()).thenReturn(Arrays.asList(trail1, trail2));

        List<TrailDto> trails = trailService.getAllTrails();

        verify(trailRepository, new Times(1)).findAllTrails();
        assertEquals(trails.size(), 2, "expecting 2 trails");
        assertEquals(trails.get(0), trail1);
        assertEquals(trails.get(1), trail2);
    }

    @DisplayName("Test save trail.")
    @Test
    void testSave() {
        //prepare mocks
        TrailDto trailDto = new TrailDto(null, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        Trail trail = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        when(trailRepository.save(Mockito.any(Trail.class))).thenReturn(trail);

        trailDto = trailService.save(trailDto);

        verify(trailRepository, new Times(1)).save(any(Trail.class));
        assertEquals(trailDto.getTrailId(), 1L, "expecting 1 as trail id");
    }


    @DisplayName("Test update trail.")
    @Test
    void testUpdate() {
        //prepare mocks
        TrailDto trailDto = new TrailDto(null, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        Trail trail = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        when(trailRepository.save(any(Trail.class))).thenReturn(trail);

        trailDto = trailService.update(1L, trailDto);

        verify(trailRepository, new Times(1)).save(any(Trail.class));
        assertEquals(trailDto.getTrailId(), 1L, "expecting 1 as trail id");
    }

}
