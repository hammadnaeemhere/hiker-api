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
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IsFutureDateImplTest {

    @InjectMocks
    private IsFutureDateImpl isFutureDate;

    @DisplayName("Test isValid when date is in future")
    @Test
    void testIsValid() {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.YEAR, 1);
        Date futureDate = c.getTime();

        assertTrue(isFutureDate.isValid(futureDate, null), "Is valid date");
    }

    @DisplayName("Test isValid when date is in past")
    @Test
    void testIsValidFalse() {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.YEAR, -1);
        Date futureDate = c.getTime();

        assertFalse(isFutureDate.isValid(futureDate, null), "Is invalid date");
    }


}
