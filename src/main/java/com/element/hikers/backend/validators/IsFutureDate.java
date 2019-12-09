package com.element.hikers.backend.validators;

import com.element.hikers.backend.validators.impl.IsFutureDateImpl;
import com.element.hikers.backend.validators.impl.ValidTrailIdImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = IsFutureDateImpl.class)
public @interface IsFutureDate {
    String message() default "{date.is_future}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
