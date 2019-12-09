package com.element.hikers.backend.validators.impl;

import com.element.hikers.backend.validators.IsFutureDate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;
import java.util.Objects;

@Component
public class IsFutureDateImpl implements ConstraintValidator<IsFutureDate, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        return Objects.isNull(date) || date.after(new Date());
    }
}
