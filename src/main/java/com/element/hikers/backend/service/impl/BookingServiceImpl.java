package com.element.hikers.backend.service.impl;

import com.element.hikers.backend.dto.BookingDto;
import com.element.hikers.backend.dto.HikerDto;
import com.element.hikers.backend.entity.Booking;
import com.element.hikers.backend.entity.Trail;
import com.element.hikers.backend.exceptions.InvalidAgeException;
import com.element.hikers.backend.exceptions.InvalidBookingKeyException;
import com.element.hikers.backend.mappers.BookingMapper;
import com.element.hikers.backend.repository.BookingRepository;
import com.element.hikers.backend.repository.TrailRepository;
import com.element.hikers.backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TrailRepository trailRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, TrailRepository trailRepository) {
        this.bookingRepository = bookingRepository;
        this.trailRepository = trailRepository;
    }

    @Override
    @Transactional
    public BookingDto save(BookingDto bookingDto) {
        Trail trail = trailRepository.findById(bookingDto.getTrailId()).get();
        validateAge(bookingDto.getHikers(), trail);
        Booking saved = bookingRepository.save(BookingMapper.bookingDtoToBooking(bookingDto, trail));

        return BookingMapper.bookingToBookingDto(saved);
    }

    /**
     * Validates hiker age against selected trail.
     *
     * @param hikers List of hikers.
     * @param trail  Selected Trail.
     *
     * @throws InvalidAgeException
     */
    private void validateAge(List<HikerDto> hikers, Trail trail) {
        hikers.forEach(hikerDto -> {
            if (hikerDto.getAge() < trail.getMinAge() || hikerDto.getAge() > trail.getMaxAge())
                throw new InvalidAgeException(trail.getMinAge(), trail.getMaxAge(), hikerDto.getEmail());
        });
    }

    @Override
    @Transactional
    public BookingDto cancel(Long bookingId, String bookingKey) {
        Optional<Booking> optionalBooking = bookingRepository.findByBookingIdAndBookingKey(bookingId, bookingKey);

        if (!optionalBooking.isPresent())
            throw new InvalidBookingKeyException();

        Booking booking = optionalBooking.get();
        booking.setIsCancelled(true);

        bookingRepository.save(booking);

        return BookingMapper.bookingToBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAll(Long trailId, Date bookingDate, Date bookingFrom, Date bookingTo, Boolean cancelled) {
        return BookingMapper.bookingToBookingDto(bookingRepository.findAll(trailId, bookingDate, bookingFrom, bookingTo, cancelled));
    }
}
