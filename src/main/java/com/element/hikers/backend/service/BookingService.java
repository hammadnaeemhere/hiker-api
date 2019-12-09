package com.element.hikers.backend.service;

import com.element.hikers.backend.dto.BookingDto;

import java.util.Date;
import java.util.List;

public interface BookingService {

    BookingDto save(BookingDto booking);

    BookingDto cancel(Long bookingId, String bookingKey);

    List<BookingDto> getAll(Long trailId, Date bookingDate, Date bookingFrom, Date bookingTo, Boolean cancelled);


}
