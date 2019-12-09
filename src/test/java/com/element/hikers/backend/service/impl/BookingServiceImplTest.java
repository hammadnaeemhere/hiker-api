package com.element.hikers.backend.service.impl;

import com.element.hikers.backend.dto.BookingDto;
import com.element.hikers.backend.dto.HikerDto;
import com.element.hikers.backend.entity.Booking;
import com.element.hikers.backend.entity.Hiker;
import com.element.hikers.backend.entity.Trail;
import com.element.hikers.backend.exceptions.InvalidAgeException;
import com.element.hikers.backend.exceptions.InvalidBookingKeyException;
import com.element.hikers.backend.repository.BookingRepository;
import com.element.hikers.backend.repository.TrailRepository;
import com.element.hikers.backend.service.BookingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TrailRepository trailRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @DisplayName("Test getAll with all possible params.")
    @Test
    void testGetAll() {
        //prepare mocks
        Long trailId = 1L;
        Date bookingDate = new Date();
        Date bookingFrom = new Date();
        Date bookingTo = new Date();
        Boolean cancelled = true;

        Trail trail1 = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        Trail trail2 = new Trail(2L, "Trail2", "13:00", "15:00", 10, 50, BigDecimal.valueOf(15.00), "EUR");

        Hiker hiker1 = new Hiker(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");
        Hiker hiker2 = new Hiker(2L, "Hiker", "Two", 40, "03468328336", "hiker2@gmail.com");


        List<Booking> bookings = Arrays.asList(new Booking(1L, bookingDate,"key1", trail1, Collections.singletonList(hiker1), true),
                new Booking(2L, bookingDate,"key2", trail2, Collections.singletonList(hiker2), true));


        when(bookingRepository.findAll(trailId, bookingDate, bookingFrom, bookingTo, cancelled)).thenReturn(bookings);

        List<BookingDto> bookingDtos = bookingService.getAll(trailId, bookingDate, bookingFrom, bookingTo, cancelled);

        verify(bookingRepository, new Times(1)).findAll(trailId, bookingDate, bookingFrom, bookingTo, cancelled);
        assertEquals(bookingDtos.size(), 2, "Expecting 2 bookings");

        //more asserts here
    }

    @DisplayName("Test save success scenario.")
    @Test
    void testSaveSuccess() {
        //prepare dto mocks
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");
        BookingDto booking = new BookingDto();
        booking.setBookingDate(new Date());
        booking.setTrailId(1L);
        booking.setHikers(Collections.singletonList(hiker));

        //prepare entity mocks
        Hiker hikerDb = new Hiker(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");
        Trail trail = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        Booking bookingDb = new Booking(1L, new Date(),"key1", trail, Collections.singletonList(hikerDb), false);

        when(trailRepository.findById(1L)).thenReturn(Optional.of(trail));
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingDb);

        booking = bookingService.save(booking);

        verify(trailRepository, new Times(1)).findById(1L);
        verify(bookingRepository, new Times(1)).save(any(Booking.class));
        assertEquals(booking.getBookingKey(), "key1", "Expecting key1 as booking key");
        assertEquals(booking.getBookingId(), 1L, "Expecting 1L as booking id");
    }

    @DisplayName("Test save success scenario.")
    @Test()
    void testSaveInvalidAgeException() {
        //prepare dto mocks
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 5, "03468328336", "hiker1@gmail.com");
        BookingDto booking = new BookingDto();
        booking.setBookingDate(new Date());
        booking.setTrailId(1L);
        booking.setHikers(Collections.singletonList(hiker));

        //prepare entity mocks
        Trail trail = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        when(trailRepository.findById(1L)).thenReturn(Optional.of(trail));

        InvalidAgeException exception = assertThrows(InvalidAgeException.class, () -> {
            bookingService.save(booking);
        });

        verify(bookingRepository, never()).save(any(Booking.class)); //save is never called
        verify(trailRepository, new Times(1)).findById(1L);
        assertEquals(exception.getMinAge(), 10, "Expecting 10 as minAge");
        assertEquals(exception.getMaxAge(), 50, "Expecting 50 as maxAge");
        assertEquals(exception.getEmail(), "hiker1@gmail.com", "Expecting hikers email address");
    }


    @DisplayName("Test booking cancel success scenario.")
    @Test()
    void testCancelBookingSuccess() {
        //prepare entity mocks
        Hiker hikerDb = new Hiker(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");
        Trail trail = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        Booking bookingDb = new Booking(1L, new Date(),"key1", trail, Collections.singletonList(hikerDb), false);

        when(bookingRepository.findByBookingIdAndBookingKey(1L, "Key")).thenReturn(Optional.of(bookingDb));

        BookingDto bookingDto = bookingService.cancel(1L, "Key");

        verify(bookingRepository, new Times(1)).findByBookingIdAndBookingKey(1L, "Key");
        verify(bookingRepository, new Times(1)).save(any(Booking.class));
        assertTrue(bookingDto.getIsCancelled(), "IsCancelled set to true");
    }

    @DisplayName("Test booking cancel invalid booking id and key scenario.")
    @Test()
    void testCancelBookingInvalidBookingKeyException() {
        when(bookingRepository.findByBookingIdAndBookingKey(1L, "Key")).thenReturn(Optional.empty());

        InvalidBookingKeyException exception = assertThrows(InvalidBookingKeyException.class, () -> {
            bookingService.cancel(1L, "Key");
        });
        verify(bookingRepository, new Times(1)).findByBookingIdAndBookingKey(1L, "Key");
        verify(bookingRepository, never()).save(any(Booking.class));
        assertNotNull(exception, "has exception");
    }


}
