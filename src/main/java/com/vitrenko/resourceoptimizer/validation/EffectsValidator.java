package com.vitrenko.resourceoptimizer.validation;

import com.vitrenko.resourceoptimizer.domain.Aggregate;
import com.vitrenko.resourceoptimizer.domain.Task;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.util.CollectionUtils.isEmpty;

public class EffectsValidator implements ConstraintValidator<CanAllocateResources, Aggregate> {

    @Override
    public boolean isValid(Aggregate value, ConstraintValidatorContext context) {
        return value.getEffectParameters() != null || !isEmpty(value.getEffects());
    }
}
