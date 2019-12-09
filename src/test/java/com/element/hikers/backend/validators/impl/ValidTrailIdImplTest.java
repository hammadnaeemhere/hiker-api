package com.element.hikers.backend.validators.impl;

import com.element.hikers.backend.entity.Trail;
import com.element.hikers.backend.repository.TrailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidTrailIdImplTest {

    @Mock
    private TrailRepository trailRepository;

    @InjectMocks
    private ValidTrailIdImpl validTrailId;

    @DisplayName("Test isValid when trail exists")
    @Test
    void testIsValid() {
        Trail trail = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        when(trailRepository.findById(1L)).thenReturn(Optional.of(trail));

        assertTrue(validTrailId.isValid(1L, null), "Trail Id is valid");
        verify(trailRepository, new Times(1)).findById(1L);
    }

    @DisplayName("Test isValid when trail doesn't exists")
    @Test
    void testIsValidFalse() {
        when(trailRepository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(validTrailId.isValid(1L, null), "Trail Id is not valid");
        verify(trailRepository, new Times(1)).findById(1L);
    }


}
