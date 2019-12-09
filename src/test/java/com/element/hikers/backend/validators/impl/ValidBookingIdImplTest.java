package com.element.hikers.backend.validators.impl;

import com.element.hikers.backend.entity.Booking;
import com.element.hikers.backend.entity.Trail;
import com.element.hikers.backend.repository.BookingRepository;
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
public class ValidBookingIdImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ValidBookingIdImpl validBookingId;

    @DisplayName("Test isValid when booking exists")
    @Test
    void testIsValid() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(new Booking()));

        assertTrue(validBookingId.isValid(1L, null), "Booking Id is valid");
        verify(bookingRepository, new Times(1)).findById(1L);
    }

    @DisplayName("Test isValid when booking doesn't exists")
    @Test
    void testIsValidFalse() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(validBookingId.isValid(1L, null), "Booking Id is not valid");
        verify(bookingRepository, new Times(1)).findById(1L);
    }


}
