package com.element.hikers.backend.validators.impl;

import com.element.hikers.backend.repository.BookingRepository;
import com.element.hikers.backend.repository.TrailRepository;
import com.element.hikers.backend.validators.ValidBookingId;
import com.element.hikers.backend.validators.ValidTrailId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Component
public class ValidBookingIdImpl implements ConstraintValidator<ValidBookingId, Long> {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public boolean isValid(Long bookingId, ConstraintValidatorContext context) {
        return !Objects.isNull(bookingId) && bookingRepository.findById(bookingId).isPresent();
    }
}
