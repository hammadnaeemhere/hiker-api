package com.element.hikers.backend.validators;

import com.element.hikers.backend.validators.impl.ValidBookingIdImpl;
import com.element.hikers.backend.validators.impl.ValidTrailIdImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = ValidBookingIdImpl.class)
public @interface ValidBookingId {
    String message() default "{booking.invalid_id}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
