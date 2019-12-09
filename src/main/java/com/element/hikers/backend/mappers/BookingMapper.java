package com.element.hikers.backend.mappers;

import com.element.hikers.backend.dto.BookingDto;
import com.element.hikers.backend.entity.Booking;
import com.element.hikers.backend.entity.Trail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingMapper {

    public static Booking bookingDtoToBooking(BookingDto bookingDto, Trail trail) {
        Booking booking = new Booking();
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setBookingKey(UUID.randomUUID().toString()); //Can use some smaller unique booking key instead of uuid
        booking.setTrail(trail);
        booking.setHikers(new ArrayList<>());
        bookingDto.getHikers().forEach(hikerDto -> {
            booking.getHikers().add(HikerMapper.hikerDtoToHiker(hikerDto));
        });

        return booking;
    }

    public static BookingDto bookingToBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBookingId(booking.getBookingId());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setBookingKey(booking.getBookingKey());
        bookingDto.setIsCancelled(booking.getIsCancelled());
        bookingDto.setTotalAmount(booking.getTrail().getTicketPrice().multiply(BigDecimal.valueOf(booking.getHikers().size())));
        bookingDto.setHikers(new ArrayList<>());
        booking.getHikers().forEach(hiker -> {
            bookingDto.getHikers().add(HikerMapper.hikerToHikerDto(hiker));
        });

        bookingDto.setTrail(TrailMapper.trailToTrailDto(booking.getTrail()));


        return bookingDto;
    }

    public static List<BookingDto> bookingToBookingDto(Iterable<Booking> bookings) {
        List<BookingDto> bookingDtos = new ArrayList<>();
        bookings.forEach(booking -> {
            bookingDtos.add(bookingToBookingDto(booking));
        });
        return bookingDtos;
    }
}
