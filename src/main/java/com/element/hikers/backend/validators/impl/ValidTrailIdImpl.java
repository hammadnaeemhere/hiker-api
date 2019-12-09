package com.element.hikers.backend.validators.impl;

import com.element.hikers.backend.repository.TrailRepository;
import com.element.hikers.backend.validators.ValidTrailId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Component
public class ValidTrailIdImpl implements ConstraintValidator<ValidTrailId, Long> {
    @Autowired
    private TrailRepository trailRepository;

    @Override
    public boolean isValid(Long trailId, ConstraintValidatorContext context) {
        return !Objects.isNull(trailId) && trailRepository.findById(trailId).isPresent();
    }
}
